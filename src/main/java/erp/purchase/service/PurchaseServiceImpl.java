// PurchaseServiceImpl.java
package erp.purchase.service;

import static erp.global.util.RowCountGuards.requireOneRowAffected;

import erp.employee.validation.EmployeeValidator;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.global.response.PageResponse;
import erp.global.util.PageParam;
import erp.global.util.Strings;
import erp.global.util.Totals;
import erp.global.util.time.DatePeriod;
import erp.global.util.time.DateRange;
import erp.global.util.time.Periods;
import erp.item.dto.internal.ItemPriceRow;
import erp.item.service.ItemService;
import erp.item.validation.ItemValidator;
import erp.purchase.domain.Purchase;
import erp.purchase.domain.PurchaseItem;
import erp.purchase.dto.internal.PurchaseCodeAndSupplierRow;
import erp.purchase.dto.internal.PurchaseDetailRow;
import erp.purchase.dto.internal.PurchaseFindRow;
import erp.purchase.dto.internal.PurchaseItemDetailRow;
import erp.purchase.dto.internal.PurchaseItemStockFindRow;
import erp.purchase.dto.request.PurchaseFindAllRequest;
import erp.purchase.dto.request.PurchaseItemSaveRequest;
import erp.purchase.dto.request.PurchaseSaveRequest;
import erp.purchase.dto.response.PurchaseCodeAndSupplierResponse;
import erp.purchase.dto.response.PurchaseDetailResponse;
import erp.purchase.dto.response.PurchaseFindResponse;
import erp.purchase.dto.response.PurchaseItemStockFindResponse;
import erp.purchase.dto.response.PurchaseItemsSummaryResponse;
import erp.purchase.enums.PurchaseStatus;
import erp.purchase.mapper.PurchaseItemMapper;
import erp.purchase.mapper.PurchaseMapper;
import erp.purchase.validation.PurchaseValidator;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseMapper purchaseMapper;
    private final PurchaseItemMapper purchaseItemMapper;

    private final ItemService itemService;

    private final EmployeeValidator employeeValidator;
    private final ItemValidator itemValidator;
    private final PurchaseValidator purchaseValidator;

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
        employeeValidator.validEmployeeIdIfPresent(employeeId, tenantId);

        // 3) 아이템 단가 일괄 조회 (N+1 제거)
        List<Long> itemIds = requestItems.stream()
            .map(PurchaseItemSaveRequest::itemId)
            .toList();
        // 아이템 존재/활성 일괄 검증
        itemValidator.validItemIdsExist(itemIds, tenantId);

        List<ItemPriceRow> priceRows = itemService.findAllItemPriceByIds(itemIds, tenantId);

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
                savePurchaseItems(newPurchaseId, requestItems, tenantId);

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


    @Override
    @Transactional(readOnly = true)
    public PageResponse<PurchaseFindResponse> findAllPurchase(PurchaseFindAllRequest request,
        long tenantId) {
        PageParam pageParam = PageParam.of(request.page(), request.size(), 20);

        DatePeriod datePeriod = request.period();
        DateRange dateRange = Periods.resolve(datePeriod, LocalDate.now());
        LocalDate startDate = dateRange.startDate();
        LocalDate endDate = dateRange.endDate();
        String code = Strings.normalizeOrNull(request.code());
        PurchaseStatus status = request.status();

        // 4) 목록 조회 (삭제 제외, tenantGuard는 XML에서 WHERE 마지막)
        List<PurchaseFindRow> rows = purchaseMapper.findAllPurchaseFindRow(
            tenantId,
            startDate,
            endDate,
            code,
            status,
            pageParam.offset(),
            pageParam.size()
        );
        if (rows.isEmpty()) {
            throw new GlobalException(ErrorStatus.NOT_REGISTERED_PURCHASE);
        }

        List<PurchaseFindResponse> responses = rows.stream()
            .map(PurchaseFindResponse::from)
            .toList();

        long total = purchaseMapper.countByPeriodAndCodeAndStatus(
            tenantId,
            startDate,
            endDate,
            code,
            status
        );

        return PageResponse.of(
            responses,
            pageParam.page(),
            total,
            pageParam.size()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PurchaseItemsSummaryResponse findPurchaseItemsSummary(long purchaseId, long tenantId) {
        purchaseValidator.validPurchaseId(purchaseId, tenantId);

        List<PurchaseItemStockFindRow> rows =
            purchaseItemMapper.findAllPurchaseItemStockFindRow(tenantId, purchaseId);
        if (rows.isEmpty()) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_PURCHASE_ITEM);
        }

        List<PurchaseItemStockFindResponse> items = rows.stream()
            .map(PurchaseItemStockFindResponse::from)
            .toList();

        return PurchaseItemsSummaryResponse.of(items);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseCodeAndSupplierResponse> findAllPurchaseCodeAndSupplier(long tenantId) {
        List<PurchaseCodeAndSupplierRow> rows =
            purchaseMapper.findAllCodeAndSupplier(tenantId);

        if (rows.isEmpty()) {
            throw new GlobalException(ErrorStatus.NOT_REGISTERED_PURCHASE);
        }

        return rows.stream()
            .map(PurchaseCodeAndSupplierResponse::from)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PurchaseDetailResponse findPurchaseDetail(long purchaseId, long tenantId) {
        purchaseValidator.validPurchaseId(purchaseId, tenantId);

        PurchaseDetailRow header = purchaseMapper.findPurchaseDetailRow(tenantId, purchaseId)
            .orElseThrow(() -> new GlobalException(ErrorStatus.NOT_FOUND_PURCHASE));

        List<PurchaseItemDetailRow> itemRows =
            purchaseItemMapper.findAllPurchaseItemDetailRow(tenantId, purchaseId);
        if (itemRows.isEmpty()) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_PURCHASE_ITEM);
        }

        return PurchaseDetailResponse.of(header, itemRows);
    }

    @Transactional
    @Override
    public void cancelPurchase(long purchaseId, long tenantId) {
        // 1) 존재/활성(삭제 제외) 선검증
        purchaseValidator.validPurchaseId(purchaseId, tenantId);

        // 2) 조건부 업데이트: SHIPPED가 아니면 CANCELLED로 변경
        int affectedRowCount = purchaseMapper.updateStatusToCancelledIfNotShipped(tenantId,
            purchaseId);

        // 3) 성공 시 즉시 종료 (추가 조회 불필요)
        if (affectedRowCount == 1) {
            return;
        }

        // 4) 실패 사유 판정: SHIPPED 여부만 확인
        if (purchaseMapper.existsShippedById(tenantId, purchaseId)) {
            throw new GlobalException(ErrorStatus.CANNOT_CANCEL_SHIPPED_PURCHASE);
        }

        // 5) 그 외(이미 CANCELLED 등) 일반 실패
        throw new GlobalException(ErrorStatus.CANCEL_PURCHASE_FAIL);
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
    private void savePurchaseItems(long newPurchaseId,
        List<PurchaseItemSaveRequest> requestItems,
        long tenantId) {
        List<PurchaseItem> items = new ArrayList<>(requestItems.size());
        for (PurchaseItemSaveRequest itemSaveRequest : requestItems) {
            long newPurchaseItemId = purchaseItemMapper.nextId();
            items.add(PurchaseItem.register(
                newPurchaseItemId, newPurchaseId,
                itemSaveRequest.itemId(), itemSaveRequest.quantity(), tenantId
            ));
        }
        int affectedItems = purchaseItemMapper.saveAll(tenantId, items);
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

    // 합계 계산(총수량, 총금액)
    private Totals computeTotals(List<PurchaseItemSaveRequest> requestItems,
        Map<Long, Integer> priceMap) {
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
