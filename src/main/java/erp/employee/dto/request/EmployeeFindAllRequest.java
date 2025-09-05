package erp.employee.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record EmployeeFindAllRequest(
        @Positive
        Long departmentId,

        @Positive
        Long positionId,

        String name,

        @PositiveOrZero
        Integer page,

        @Positive
        Integer size
) {
}
