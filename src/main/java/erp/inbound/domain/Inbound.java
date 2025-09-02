package erp.inbound.domain;

import erp.global.base.TimeStamped;
import erp.global.shared.enums.FulfillmentStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Inbound extends TimeStamped {
    private Long inboundId;
    private String code;
    private LocalDate inboundDate;
    private FulfillmentStatus status;
    private long employeeId;
    private long purchaseId;
    private long companyId;
}
