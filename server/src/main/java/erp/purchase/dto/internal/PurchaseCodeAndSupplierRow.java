package erp.purchase.dto.internal;

import jakarta.validation.constraints.NotNull;

public record PurchaseCodeAndSupplierRow(
    @NotNull
    String code,
    @NotNull
    String supplier
) {

}
