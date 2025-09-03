package erp.purchase.service;

import erp.employee.mapper.EmployeeMapper;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.global.util.Strings;
import erp.item.dto.internal.ItemPriceRow;
import erp.item.mapper.ItemMapper;
import erp.purchase.domain.Purchase;
import erp.purchase.domain.PurchaseItem;
import erp.purchase.dto.request.PurchaseItemSaveRequest;
import erp.purchase.dto.request.PurchaseSaveRequest;
import erp.purchase.enums.PurchaseStatus;
import erp.purchase.mapper.PurchaseItemMapper;
import erp.purchase.mapper.PurchaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseMapper purchaseMapper;
    private final PurchaseItemMapper purchaseItemMapper;
    private final EmployeeMapper employeeMapper;
    private final ItemMapper itemMapper;

    private static final String ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // 가독성 Base32
    private static final int TOKEN_LEN = 8;
    private static final int MAX_TRY = 5;
    private final SecureRandom secureRandom = new SecureRandom();


    @Override
    @Transactional
    public long save(PurchaseSaveRequest request, long tenantId) {
        String supplier = Strings.normalizeOrNull(request.supplier());
        LocalDate purchaseDate = request.purchaseDate();
        Long employeeId = request.employeeId();
        List<PurchaseItemSaveRequest> requestItems = request.items();

        // item 중복 검사
        Set<Long> set = new HashSet<>();
        for (PurchaseItemSaveRequest requestItem : requestItems) {
            if (!set.add(requestItem.itemId()))
                throw new GlobalException(ErrorStatus.DUPLICATE_ITEM);
        }

        validEmployeeIdIfPresent(employeeId, tenantId);


        // 아이템 단가 일괄 조회 (N+1 제거)
        List<Long> itemIds = requestItems.stream().map(PurchaseItemSaveRequest::itemId).toList();
        List<ItemPriceRow> priceRows = itemMapper.findAllPriceByIds(tenantId, itemIds);
        Map<Long, Integer> priceMap = priceRows.stream()
                .collect(Collectors.toMap(ItemMapper.ItemPriceRow::itemId,
                        ItemMapper.ItemPriceRow::price));

        for (Long itemId : itemIds) {
            if (!priceMap.containsKey(itemId))
                throw new GlobalException(ErrorStatus.NOT_FOUND_ITEM);
        }

        // 3) 합계 계산
        int totalQuantity = 0;
        int totalAmount = 0;
        for (PurchaseItemSaveRequest purchaseItemSaveRequest : requestItems) {
            int quantity = purchaseItemSaveRequest.quantity();
            int unitPrice = priceMap.get(purchaseItemSaveRequest.itemId());
            totalQuantity += quantity;
            totalAmount = Math.addExact(totalAmount, Math.multiplyExact(quantity, unitPrice));
        }

        // 4) 발주 코드 생성
        int year = purchaseDate.getYear();
        for (int attempt = 0; attempt < MAX_TRY; attempt++) {
            String purchaseCode = buildPurchaseCode(year);
            try {
                long newPurchaseId = purchaseMapper.nextId();

                // 도메인 팩토리 사용 (register)
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

                int affected = purchaseMapper.save(tenantId, purchase);
                requireOneRowAffected(affected, ErrorStatus.CREATE_PURCHASE_FAIL);

                // 5) 아이템 일괄 저장
                List<PurchaseItem> items = new ArrayList<>(requestItems.size());
                for (PurchaseItemSaveRequest ir : requestItems) {
                    long newPurchaseItemId = purchaseItemMapper.nextId();
                    items.add(PurchaseItem.register(newPurchaseItemId, newPurchaseId, ir.itemId(), ir.quantity()));
                }

                int affectedItems = purchaseItemMapper.saveAll(tenantId, items);
                if (affectedItems != items.size())
                    throw new GlobalException(ErrorStatus.CREATE_PURCHASE_ITEM_FAIL);
                requireNonZeroRowsAffected(affectedItems, ErrorStatus.CREATE_PURCHASE_ITEM_FAIL);

                return newPurchaseId;

            } catch (DuplicateKeyException e) {
                if (attempt == MAX_TRY - 1)
                    throw new GlobalException(ErrorStatus.CREATE_PURCHASE_FAIL);
            }
        }

        throw new GlobalException(ErrorStatus.CREATE_PURCHASE_FAIL);
    }

    private void validEmployeeIdIfPresent(Long employeeId, long tenantId) {
        if (employeeId != null && !employeeMapper.existsById(tenantId, employeeId)) {
            throw new GlobalException(ErrorStatus.EMPLOYEE_NOT_FOUND);
        }
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
