package erp.purchase.domain;

import erp.global.base.TimeStamped;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Purchase extends TimeStamped {
    private Long purchaseId;
    private Long employeeId;
    private String supplier;
    private String code;
    private LocalDate purchaseDate;
}
