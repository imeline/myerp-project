package erp.outbound.domain;

import erp.global.base.TimeStamped;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Outbound extends TimeStamped {
    private Long outboundId;
    private String code;
    private LocalDate outboundDate;
    private Long employeeId;
    private Long orderId;
}
