package erp.item.domain;

import erp.global.base.TimeStamped;
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
    private String category;
    private long companyId;
    private LocalDateTime deletedAt;
}
