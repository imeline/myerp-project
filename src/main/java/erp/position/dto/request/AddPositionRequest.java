package erp.position.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddPositionRequest(
        @NotBlank
        String name,
        @NotNull
        Integer levelNo
) {
}
