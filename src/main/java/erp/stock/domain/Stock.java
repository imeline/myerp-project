package erp.stock.domain;

import erp.global.base.TimeStamped;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Stock extends TimeStamped {
    private Long id;
    private String warehouse;
    private Integer quantity;
    private Integer safeQuantity;
    private Long itemId;
}
