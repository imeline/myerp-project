package erp.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank
        @Email(message = "올바른 이메일 형식이어야 합니다.")
        String loginEmail,

        @NotBlank
        @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
        String password,

        @NotBlank
        String empNo,

        @NotBlank
        String name,

        @NotBlank
        String department,

        @NotBlank
        String position,

        @NotBlank
        String phone,

        @NotNull
        long companyId
) {

}


