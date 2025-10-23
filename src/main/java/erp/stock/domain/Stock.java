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
    private int allocatedQuantity;
    private int onHandQuantity;
    private long itemId;

    public static Stock resister(long stockId, String warehouse,
                                 int initialQuantity, long itemId) {
        return Stock.builder()
                .stockId(stockId)
                .warehouse(warehouse)
                .onHandQuantity(initialQuantity)
                .allocatedQuantity(0)
                .itemId(itemId)
                .build();
    }
}