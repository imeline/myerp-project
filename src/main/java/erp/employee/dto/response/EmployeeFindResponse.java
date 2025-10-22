package erp.employee.dto.response;

import erp.employee.dto.internal.EmployeeFindRow;
import lombok.Builder;

@Builder
public record EmployeeFindResponse(
        String name,
        String empNo,
        long departmentId,
        String departmentName,
        long positionId,
        String positionName,
        String phone
) {
    public static EmployeeFindResponse from(EmployeeFindRow row) {
        return EmployeeFindResponse.builder()
                .name(row.name())
                .empNo(row.empNo())
                .departmentId(row.departmentId())
                .departmentName(row.departmentName())
                .positionId(row.positionId())
                .positionName(row.positionName())
                .phone(row.phone())
                .build();
    }
}
