package erp.purchase.service;

import erp.employee.mapper.EmployeeMapper;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.global.util.Strings;
import erp.item.dto.internal.ItemPriceRow;
import erp.item.mapper.ItemMapper;
import erp.purchase.domain.Purchase;
import erp.purchase.domain.PurchaseItem;
import erp.purchase.dto.internal.Totals;
import erp.purchase.dto.request.PurchaseItemSaveRequest;
import erp.purchase.dto.request.PurchaseSaveRequest;
import erp.purchase.enums.PurchaseStatus;
import erp.purchase.mapper.PurchaseItemMapper;
import erp.purchase.mapper.PurchaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static erp.global.util.RowCountGuards.requireOneRowAffected;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseMapper purchaseMapper;
    private final PurchaseItemMapper purchaseItemMapper;
    private final EmployeeMapper employeeMapper;
    private final ItemMapper itemMapper;

    private static final String ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int TOKEN_LEN = 8;
    private static final int MAX_TRY = 5;
    private final SecureRandom secureRandom = new SecureRandom();


    @Override
    @Transactional
    public long savePurchaseAndPurchaseItems(PurchaseSaveRequest request, long tenantId) {
        String supplier = Strings.normalizeOrNull(request.supplier());
        LocalDate purchaseDate = request.purchaseDate();
        Long employeeId = request.employeeId();
        List<PurchaseItemSaveRequest> requestItems = request.items();

        // 1) 요청 DTO 내 itemId 중복 검사 (동일 품목 중복 방지)
        validItemIdsUniqueInRequest(requestItems);

        // 2) 직원 존재, 활성 유효성 검사 (회사 스코프 내)
        validEmployeeIdIfPresent(employeeId, tenantId);

        // 3) 아이템 단가 일괄 조회 (N+1 제거)
        List<Long> itemIds = requestItems.stream()
                .map(PurchaseItemSaveRequest::itemId)
                .toList();
        // 아이템 존재/활성 일괄 검증
        validItemIdsIfPresent(itemIds, tenantId);

        List<ItemPriceRow> priceRows = itemMapper.findAllPriceByIds(tenantId, itemIds);

        // 4) 조회 결과를 Map<Long itemId, Integer price>로 변환
        Map<Long, Integer> priceMap = priceRows.stream()
                .collect(Collectors.toMap(ItemPriceRow::itemId, ItemPriceRow::price));

        // 5) 합계 계산(총수량, 총금액)
        Totals totals = computeTotals(requestItems, priceMap);
        int totalQuantity = totals.totalQuantity();
        int totalAmount = totals.totalAmount();

        // 6) 발주 코드 생성 & 저장 (유니크 충돌 시 재시도)
        // MAX_TRY 인 5회 안에 성공하지 못할 확률은 극히 낮음(=사실상 불가능)
        int year = purchaseDate.getYear();
        for (int attempt = 0; attempt < MAX_TRY; attempt++) {
            String purchaseCode = buildPurchaseCode(year);

            // 발주코드 unique를 먼저 검사하고 저장을 하면 동시성 문제가 발생 가능
            // 저장 시도 후 DB에서 잡아주는 유니크 제약조건에 의존하는 것이 안전
            // → DuplicateKeyException 발생 시 재시도
            try {
                // 6-1) Purchase 저장
                long newPurchaseId = savePurchase(
                        purchaseCode,
                        supplier,
                        purchaseDate,
                        totalQuantity,
                        totalAmount,
                        employeeId,
                        tenantId
                );

                // 6-2) PurchaseItem 배치 저장
                savePurchaseItems(newPurchaseId, requestItems);

                // 7) 성공: 생성된 purchase_id 반환
                return newPurchaseId;

            } catch (DuplicateKeyException e) {
                // 발주코드 유니크 충돌 시 재시도, 마지막 시도 실패 시 예외
                if (attempt == MAX_TRY - 1) {
                    throw new GlobalException(ErrorStatus.CREATE_PURCHASE_FAIL);
                }
            }
        }

        // 8) 방어적 반환 (정상 흐름상 도달하지 않음)
        throw new GlobalException(ErrorStatus.CREATE_PURCHASE_FAIL);
    }

    // Purchase 저장
    private long savePurchase(String purchaseCode,
                              String supplier,
                              LocalDate purchaseDate,
                              int totalQuantity,
                              int totalAmount,
                              Long employeeId,
                              long tenantId) {
        long newPurchaseId = purchaseMapper.nextId();
        Purchase purchase = Purchase.register(
                newPurchaseId,
                purchaseCode,
                supplier,
                purchaseDate,
                totalQuantity,
                totalAmount,
                PurchaseStatus.CONFIRMED,
                employeeId,
                tenantId
        );
        int affectedRowCount = purchaseMapper.save(tenantId, purchase);
        requireOneRowAffected(affectedRowCount, ErrorStatus.CREATE_PURCHASE_FAIL);
        return newPurchaseId;
    }

    // PurchaseItem 배치 저장
    private void savePurchaseItems(long newPurchaseId, List<PurchaseItemSaveRequest> requestItems) {
        List<PurchaseItem> items = new ArrayList<>(requestItems.size());
        for (PurchaseItemSaveRequest itemSaveRequest : requestItems) {
            long newPurchaseItemId = purchaseItemMapper.nextId();
            items.add(PurchaseItem.register(
                    newPurchaseItemId, newPurchaseId, itemSaveRequest.itemId(), itemSaveRequest.quantity()
            ));
        }
        int affectedItems = purchaseItemMapper.saveAll(items);
        if (affectedItems != items.size()) {
            throw new GlobalException(ErrorStatus.CREATE_PURCHASE_ITEM_FAIL);
        }
    }

    // 요청 DTO 내 itemId 중복 검사 (동일 품목 중복 방지)
    private void validItemIdsUniqueInRequest(List<PurchaseItemSaveRequest> requestItems) {
        Set<Long> set = new HashSet<>();
        for (PurchaseItemSaveRequest requestItem : requestItems) {
            if (!set.add(requestItem.itemId())) {
                throw new GlobalException(ErrorStatus.DUPLICATE_ITEM);
            }
        }
    }

    // 직원 존재, 활성 유효성 검사 (회사 스코프 내)
    private void validEmployeeIdIfPresent(Long employeeId, long tenantId) {
        if (employeeId != null && !employeeMapper.existsById(tenantId, employeeId)) {
            throw new GlobalException(ErrorStatus.EMPLOYEE_NOT_FOUND);
        }
    }

    // itmes 존재/활성 일괄 검증
    private void validItemIdsIfPresent(List<Long> itemIds, long tenantId) {
        // existsByIds: 모든 ID가 활성(삭제 아님)으로 존재하면 true를 반환하도록 Mapper 구현
        if (!itemMapper.existsByIds(tenantId, itemIds)) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_ITEM);
        }
    }

    // 합계 계산(총수량, 총금액)
    private Totals computeTotals(List<PurchaseItemSaveRequest> requestItems, Map<Long, Integer> priceMap) {
        int totalQuantity = 0;
        int totalAmount = 0;
        for (PurchaseItemSaveRequest itemSaveRequest : requestItems) {
            int quantity = itemSaveRequest.quantity();
            Integer unitPrice = priceMap.get(itemSaveRequest.itemId());
            if (unitPrice == null) { // 방어: 가격 매핑 누락 시 차단
                throw new GlobalException(ErrorStatus.NOT_FOUND_ITEM);
            }
            totalQuantity += quantity;
            // 오버플로우 방지 메서드 사용
            totalAmount = Math.addExact(totalAmount, Math.multiplyExact(quantity, unitPrice));
        }
        return new Totals(totalQuantity, totalAmount);
    }


    private String buildPurchaseCode(int year) {
        return "PO-" + year + "-" + randomToken();
    }

    private String randomToken() {
        StringBuilder sb = new StringBuilder(PurchaseServiceImpl.TOKEN_LEN);
        for (int i = 0; i < PurchaseServiceImpl.TOKEN_LEN; i++) {
            sb.append(ALPHABET.charAt(secureRandom.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

}
