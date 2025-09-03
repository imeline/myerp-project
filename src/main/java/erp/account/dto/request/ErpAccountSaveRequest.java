package erp.account.dto.request;

import erp.account.enums.ErpAccountRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ErpAccountSaveRequest(
        @NotNull
        Long employeeId,
        @NotBlank
        String loginEmail,
        @NotBlank
        String rawPassword,
        @NotNull
        ErpAccountRole role
) {
    public static ErpAccountSaveRequest of(
            Long employeeId,
            String loginEmail,
            String rawPassword,
            ErpAccountRole role
    ) {
        return ErpAccountSaveRequest.builder()
                .employeeId(employeeId)
                .loginEmail(loginEmail)
                .rawPassword(rawPassword)
                .role(role)
                .build();
    }
}
