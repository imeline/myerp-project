package erp.company.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddCompanyRequest(
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
