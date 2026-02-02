package erp.position.dto.request;

import jakarta.validation.constraints.NotNull;

public record PositionLevelNoRequest(
        @NotNull
        int levelNo
) {
}
