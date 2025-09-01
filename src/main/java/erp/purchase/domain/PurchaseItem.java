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
}
