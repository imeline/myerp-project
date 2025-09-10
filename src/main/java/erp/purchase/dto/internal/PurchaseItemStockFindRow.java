package erp.purchase.dto.internal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseItemStockFindRow(
    @NotNull
    String code,

    @NotNull
    String name,

    @NotNull
    @Positive
    Integer quantity,

    @NotNull
    @Positive
    Integer unitPrice,

    @NotNull
    @Positive
    Integer subtotal,                    // quantity * unitPrice

    @NotNull
    @Positive
    Integer onHandStock,                 // 현재 보유 수량 (On-hand)

    @NotNull
    @Positive
    Integer allocatedStock,              // 할당(예약) 수량 (Allocated)

    @NotNull
    @Positive
    Integer availableStock,              // 가용 재고 = onHandStock - allocatedStock

    @NotNull
    @Positive
    Integer availableStockAfterInbound   // 입고 후 가용 재고 = availableStock + quantity
) {

}
