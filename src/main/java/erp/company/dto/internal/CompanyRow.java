package erp.company.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompanyRow(
        @NotNull
        Long companyId,
        @NotBlank
        String name,
        @NotBlank
        String bizNo,
        @NotBlank
        String address,
        @NotBlank
        String phone,
        @NotBlank
        String createdAt
) {
}
