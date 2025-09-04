package erp.purchase.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PurchaseItem {
    private Long purchaseItemId;
    private int quantity;
    private long purchaseId;
    private long itemId;
    private long companyId;

    public static PurchaseItem register(
            long purchaseItemId, long purchaseId, long itemId, int quantity, long companyId) {
        return PurchaseItem.builder()
                .purchaseItemId(purchaseItemId)
                .purchaseId(purchaseId)
                .itemId(itemId)
                .quantity(quantity)
                .companyId(companyId)
                .build();
    }
}
