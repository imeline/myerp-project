package erp.order.domain;


import erp.global.base.TimeStamped;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order extends TimeStamped {
    private Long id;
    private Long memberId;
    private String customer;
    private LocalDate orderDate;
}
