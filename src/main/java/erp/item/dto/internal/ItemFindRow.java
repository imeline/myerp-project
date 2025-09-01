package erp.item.dto.internal;

import erp.item.enums.ItemCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ItemFindRow(
        Long itemId,
        String code,
        String name,
        BigDecimal price,
        String unit,
        ItemCategory category,
        LocalDateTime createdAt
) {
}
