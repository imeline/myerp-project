package erp.item.domain;

import erp.global.base.TimeStamped;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Item extends TimeStamped {
    private Long id;
    private String name;
    private String code;
    private BigDecimal price;
    private String unit;
    private String category;
}
