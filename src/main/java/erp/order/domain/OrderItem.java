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

    public static OrderItem register(long orderItemId, int quantity, long orderId,
                                     long itemId) {
        return OrderItem.builder()
                .orderItemId(orderItemId)
                .quantity(quantity)
                .orderId(orderId)
                .itemId(itemId)
                .build();
    }
}
