package erp.item.domain;

import erp.global.base.TimeStamped;
import erp.item.enums.ItemCategory;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Item extends TimeStamped {
    private Long itemId;
    private String name;
    private String code;
    private BigDecimal price;
    private String unit;
    private ItemCategory category;
    private LocalDateTime deletedAt;

    public static Item register(Long itemId, String name, String code,
                                BigDecimal price, String unit, ItemCategory category) {
        return Item.builder()
                .itemId(itemId)
                .name(name)
                .code(code)
                .price(price)
                .unit(unit)
                .category(category)
                .build();
    }

    public static Item update(Long itemId, String name, BigDecimal price,
                              String unit, ItemCategory category) {
        return Item.builder()
                .itemId(itemId)
                .name(name)
                .price(price)
                .unit(unit)
                .category(category)
                .build();
    }
}
