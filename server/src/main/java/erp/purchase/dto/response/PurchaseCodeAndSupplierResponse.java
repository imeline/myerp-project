package erp.purchase.dto.response;

import erp.purchase.dto.internal.PurchaseCodeAndSupplierRow;
import lombok.Builder;

@Builder
public record PurchaseCodeAndSupplierResponse(
    String code,
    String supplier
) {

    public static PurchaseCodeAndSupplierResponse from(PurchaseCodeAndSupplierRow row) {
        return PurchaseCodeAndSupplierResponse.builder()
            .code(row.code())
            .supplier(row.supplier())
            .build();
    }
}
