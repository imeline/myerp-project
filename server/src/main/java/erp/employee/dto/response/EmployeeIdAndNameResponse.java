package erp.employee.dto.response;

import erp.employee.dto.internal.EmployeeIdAndNameRow;
import lombok.Builder;

@Builder
public record EmployeeIdAndNameResponse(
        long employeeId,
        String name
) {
    public static EmployeeIdAndNameResponse from(EmployeeIdAndNameRow row) {
        return EmployeeIdAndNameResponse.builder()
                .employeeId(row.employeeId())
                .name(row.name())
                .build();
    }
}
