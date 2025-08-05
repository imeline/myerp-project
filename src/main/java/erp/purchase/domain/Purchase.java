package erp.purchase.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Purchase {
    private Long id;
    private Integer quantity;
    private Long purchaseId;
    private Long itemId;
}
