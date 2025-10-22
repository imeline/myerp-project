package erp.item.dto.internal;

import jakarta.validation.constraints.NotNull;

public record ItemPriceRow(
        @NotNull
        Long itemId,
        @NotNull
        Integer price
) {
}
