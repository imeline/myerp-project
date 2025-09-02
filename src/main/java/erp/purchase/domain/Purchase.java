package erp.purchase.domain;

import erp.global.base.TimeStamped;
import erp.global.shared.enums.FulfillmentStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Purchase extends TimeStamped {
    private Long purchaseId;
    private String code;
    private String supplier;
    private LocalDate purchaseDate;
    private Integer totalQuantity;
    private Integer totalAmount;
    private FulfillmentStatus status;
    private long employeeId;
    private long companyId;
}
