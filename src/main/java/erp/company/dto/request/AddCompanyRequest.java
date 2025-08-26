package erp.company.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddCompanyRequest(
        @NotBlank
        String name,
        @NotBlank
        @Pattern(regexp = "\\d{3}-\\d{2}-\\d{5}")
        String bizNo,
        @NotBlank
        String address,
        @NotBlank
        @Pattern(regexp = "0\\d{1,2}-\\d{3,4}-\\d{4}")
        String phone
) {

}
