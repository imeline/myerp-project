package erp.item.dto.request;

import erp.item.enums.ItemCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ItemFindAllRequest(
        @NotNull
        @PositiveOrZero
        Integer page,
        @NotNull
        @Positive
        Integer size,
        String name,
        ItemCategory category
) {
}
