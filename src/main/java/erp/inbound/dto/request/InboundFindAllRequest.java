package erp.inbound.dto.request;

import erp.global.util.time.DatePeriod;
import jakarta.validation.constraints.NotNull;

public record InboundFindAllRequest(
        DatePeriod period,
        String code,
        @NotNull
        Integer page,
        @NotNull
        Integer size
) {
}
