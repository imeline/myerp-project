package erp.account.dto.internal;

import jakarta.validation.constraints.NotBlank;

public record LoginUserInfoRow(
        @NotBlank
        String uuid,
        @NotBlank
        String role,
        @NotBlank
        String passwordHash,
        @NotBlank
        String name
) {
}
