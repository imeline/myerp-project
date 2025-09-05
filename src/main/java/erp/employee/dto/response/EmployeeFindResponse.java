package erp.employee.dto.response;

import erp.employee.dto.internal.EmployeeFindRow;
import lombok.Builder;

@Builder
public record EmployeeFindResponse(
        String name,
        String empNo,
        String departmentName,
        String positionName,
        String phone
) {
    public static EmployeeFindResponse from(EmployeeFindRow row) {
        return EmployeeFindResponse.builder()
                .name(row.name())
                .empNo(row.empNo())
                .departmentName(row.departmentName())
                .positionName(row.positionName())
                .phone(row.phone())
                .build();
    }
}
