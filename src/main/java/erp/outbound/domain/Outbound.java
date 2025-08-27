package erp.outbound.domain;

import erp.global.base.TimeStamped;
import erp.shared.enums.DocumentStatus;
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
    private DocumentStatus status;
    private long employeeId;
    private long orderId;
    private long companyId;
}
