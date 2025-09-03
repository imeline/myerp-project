package erp.employee.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record EmployeeSaveRequest(
        @NotBlank
        String empNo,
        @NotBlank
        String name,
        @NotBlank
        String phone,
        @NotNull
        Long departmentId,
        @NotNull
        Long positionId
) {
    public static EmployeeSaveRequest of(
            String empNo,
            String name,
            String phone,
            Long departmentId,
            Long positionId
    ) {
        return EmployeeSaveRequest.builder()
                .empNo(empNo)
                .name(name)
                .phone(phone)
                .departmentId(departmentId)
                .positionId(positionId)
                .build();
    }
}
