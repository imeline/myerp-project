package erp.item.dto.response;

import erp.item.dto.internal.ItemFindRow;
import erp.item.enums.ItemCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ItemFindResponse(
        Long itemId,
        String code,
        String name,
        BigDecimal price,
        String unit,
        ItemCategory category,
        LocalDateTime createdAt
) {
    public static ItemFindResponse from(ItemFindRow row) {
        return new ItemFindResponse(
                row.itemId(),
                row.code(),
                row.name(),
                row.price(),
                row.unit(),
                row.category(),
                row.createdAt()
        );
    }
}
