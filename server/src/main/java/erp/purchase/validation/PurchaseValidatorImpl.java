package erp.purchase.validation;

import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.purchase.dto.request.PurchaseItemSaveRequest;
import erp.purchase.mapper.PurchaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PurchaseValidatorImpl implements PurchaseValidator {

    private final PurchaseMapper purchaseMapper;

    @Override
    public void validPurchaseIdIfPresent(long purchaseId, long tenantId) {
        if (!purchaseMapper.existsById(tenantId, purchaseId)) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_PURCHASE);
        }
    }

    // 요청 DTO 내 itemId 중복 검사 (동일 품목 중복 방지)
    @Override
    public void validItemIdsUniqueInRequest(List<PurchaseItemSaveRequest> requestItems) {
        Set<Long> set = new HashSet<>();
        for (PurchaseItemSaveRequest requestItem : requestItems) {
            if (!set.add(requestItem.itemId())) {
                throw new GlobalException(ErrorStatus.DUPLICATE_ITEM);
            }
        }
    }
}

