package erp.employee.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record EmployeeFindAllRequest(
        Long departmentId,

        Long positionId,

        String name,

        @NotNull
        @PositiveOrZero
        Integer page,

        @NotNull
        @Positive
        Integer size
) {
}
