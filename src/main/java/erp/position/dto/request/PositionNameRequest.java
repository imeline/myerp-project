package erp.position.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PositionNameRequest(
        @NotBlank
        @Size(max = 50)
        String name
) {
}
