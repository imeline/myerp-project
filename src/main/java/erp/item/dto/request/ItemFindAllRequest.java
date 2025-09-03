package erp.item.dto.request;

import erp.item.enums.ItemCategory;
import jakarta.validation.constraints.NotNull;

public record ItemFindAllRequest(
        @NotNull
        Integer page,
        @NotNull
        Integer size,
        String name,
        ItemCategory category
) {
}
