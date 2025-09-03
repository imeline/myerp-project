package erp.item.dto.response;

import erp.item.dto.internal.ItemIdAndNameRow;
import lombok.Builder;

@Builder
public record ItemIdAndNameResponse(
        long itemId,
        String name
) {
    public static ItemIdAndNameResponse from(ItemIdAndNameRow row) {
        return ItemIdAndNameResponse.builder()
                .itemId(row.itemId())
                .name(row.name())
                .build();
    }
}
