package erp.employee.dto.response;

import erp.employee.dto.internal.EmployeeFindAllRow;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EmployeeFindAllResponse(
        Long employeeId,
        String empNo,
        String name,
        String departmentName,
        String positionName,
        String phone,
        String email,
        LocalDateTime createdAt
) {
    public static EmployeeFindAllResponse from(EmployeeFindAllRow row) {
        return EmployeeFindAllResponse.builder()
                .employeeId(row.employeeId())
                .empNo(row.empNo())
                .name(row.name())
                .departmentName(row.departmentName())
                .positionName(row.positionName())
                .phone(row.phone())
                .email(row.email())
                .createdAt(row.createdAt())
                .build();
    }
}
