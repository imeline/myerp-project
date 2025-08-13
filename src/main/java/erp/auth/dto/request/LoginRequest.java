package erp.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        @Email(message = "올바른 이메일 형식이어야 합니다.")
        String loginEmail,

        @NotBlank
        String password
) {

}
