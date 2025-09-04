package erp.stock.domain;

import erp.global.base.TimeStamped;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Stock extends TimeStamped {
    private Long stockId;
    private String warehouse;
    private int currentQuantity;
    private int reservedQuantity;
    private long itemId;
    private long companyId;
}
