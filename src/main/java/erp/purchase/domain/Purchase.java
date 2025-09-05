package erp.purchase.domain;

import erp.global.base.TimeStamped;
import erp.purchase.enums.PurchaseStatus;
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
    private int totalQuantity;
    private int totalAmount;
    private PurchaseStatus status;
    private long employeeId;
    private long companyId;

    public static Purchase register(
            long purchaseId,
            String code,
            String supplier,
            LocalDate purchaseDate,
            int totalQuantity,
            int totalAmount,
            PurchaseStatus status,
            long employeeId,
            long companyId
    ) {
        return Purchase.builder()
                .purchaseId(purchaseId)
                .code(code)
                .supplier(supplier)
                .purchaseDate(purchaseDate)
                .totalQuantity(totalQuantity)
                .totalAmount(totalAmount)
                .status(status)
                .employeeId(employeeId)
                .companyId(companyId)
                .build();
    }
}
