package erp.company.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModifyCompanyRequest(
        @NotNull
        long companyId,
        @NotBlank
        String name,
        @NotBlank
        String bizNo,
        @NotBlank
        String address,
        @NotBlank
        String phone
) {
}
