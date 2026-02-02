package erp.item.dto.internal;

import erp.item.enums.ItemCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ItemFindRow(
        @NotNull
        Long itemId,
        @NotBlank
        String code,
        @NotBlank
        String name,
        @NotNull
        BigDecimal price,
        @NotBlank
        String unit,
        @NotNull
        ItemCategory category,
        @NotNull
        LocalDateTime createdAt
) {
}
