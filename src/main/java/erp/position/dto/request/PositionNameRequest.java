package erp.position.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PositionNameRequest(
        @NotBlank
        String name
) {
}
