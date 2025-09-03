package erp.item.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemIdAndNameRow(
        @NotNull
        Long itemId,
        @NotBlank
        String itemName
) {
}
