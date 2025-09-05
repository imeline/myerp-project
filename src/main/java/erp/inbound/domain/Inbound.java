package erp.inbound.domain;

import erp.global.base.TimeStamped;
import erp.inbound.enums.InboundStatus;
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
    private InboundStatus status;
    private long employeeId;
    private long purchaseId;
    private long companyId;

    public static Inbound register(
            long inboundId,
            String code,
            LocalDate inboundDate,
            InboundStatus status,
            long employeeId,
            long purchaseId,
            long companyId
    ) {
        return Inbound.builder()
                .inboundId(inboundId)
                .code(code)
                .inboundDate(inboundDate)
                .status(status)
                .employeeId(employeeId)
                .purchaseId(purchaseId)
                .companyId(companyId)
                .build();
    }
}
