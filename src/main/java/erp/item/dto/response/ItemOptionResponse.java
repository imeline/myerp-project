package erp.item.dto.response;

import erp.item.dto.internal.ItemOptionRow;
import lombok.Builder;

@Builder
public record ItemOptionResponse(
    long itemId,
    String name,
    String code
) {

    public static ItemOptionResponse from(ItemOptionRow row) {
        return ItemOptionResponse.builder()
            .itemId(row.itemId())
            .name(row.name())
            .code(row.code())
            .build();
    }
}
