package erp.item.dto.response;

import erp.item.domain.Item;
import erp.item.enums.ItemCategory;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record ItemInfoResponse(
        String name,
        String code,
        BigDecimal price,
        String unit,
        ItemCategory category,
        LocalDateTime createdAt
) {
    public static ItemInfoResponse from(Item item) {
        return ItemInfoResponse.builder()
                .name(item.getName())
                .code(item.getCode())
                .price(item.getPrice())
                .unit(item.getUnit())
                .category(item.getCategory())
                .createdAt(item.getCreatedAt())
                .build();
    }
}
