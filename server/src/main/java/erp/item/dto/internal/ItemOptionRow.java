package erp.item.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemOptionRow(
    @NotNull
    Long itemId,
    @NotBlank
    String name,
    @NotBlank
    String code
) {

}
