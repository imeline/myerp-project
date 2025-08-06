package erp.inbound.domain;

import erp.global.base.TimeStamped;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Inbound extends TimeStamped {
    private Long inboundId;
    private LocalDate inboundDate;
    private String status;
    private Long employeeId;
    private Long purchaseId;
}
