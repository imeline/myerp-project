package erp.purchase.validation;

import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.purchase.mapper.PurchaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseValidatorImpl implements PurchaseValidator {

    private final PurchaseMapper purchaseMapper;

    @Override
    public void validPurchaseId(Long purchaseId, long tenantId) {
        if (purchaseId == null || !purchaseMapper.existsById(tenantId, purchaseId)) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_PURCHASE);
        }
    }
}

