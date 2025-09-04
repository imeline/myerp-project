package erp.company.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CompanyFindAllRequest(
        String name,
        @NotNull
        @PositiveOrZero
        Integer page,
        @NotNull
        @Positive
        Integer size
) {
}
