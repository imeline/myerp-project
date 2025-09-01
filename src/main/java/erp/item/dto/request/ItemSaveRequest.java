package erp.item.dto.request;

import erp.item.enums.ItemCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ItemSaveRequest(
        @NotBlank
        String name,

        @NotBlank
        String code,

        @NotNull
        @PositiveOrZero
        BigDecimal price,

        @NotBlank
        String unit,

        @NotNull
        ItemCategory category
) {
}
