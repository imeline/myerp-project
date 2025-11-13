package erp.auth.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefreshTokenRow(
        @NotNull
        Long refreshTokenId,

        @NotBlank
        String token
) {

}
