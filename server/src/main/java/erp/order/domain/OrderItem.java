package erp.order.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItem {
    private Long orderItemId;
    private int quantity;
    private long orderId;
    private long itemId;
    private long companyId;
}
