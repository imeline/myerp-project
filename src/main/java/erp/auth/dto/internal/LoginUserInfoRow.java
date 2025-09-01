package erp.auth.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginUserInfoRow(
        @NotBlank
        String uuid,
        @NotBlank
        String role,
        @NotBlank
        String passwordHash,
        @NotBlank
        String name,
        @NotNull
        Long tenantId
) {
}
