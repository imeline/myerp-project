package erp.company.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CompanyFindAllRequest(
        String name,
        @PositiveOrZero
        Integer page,
        @Positive
        Integer size
) {
}
