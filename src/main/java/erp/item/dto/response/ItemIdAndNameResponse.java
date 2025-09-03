package erp.item.dto.response;

import erp.item.dto.internal.ItemIdAndNameRow;
import lombok.Builder;

@Builder
public record ItemIdAndNameResponse(
        long itemId,
        String itemName
) {
    public static ItemIdAndNameResponse from(ItemIdAndNameRow row) {
        return ItemIdAndNameResponse.builder()
                .itemId(row.itemId())
                .itemName(row.itemName())
                .build();
    }
}
