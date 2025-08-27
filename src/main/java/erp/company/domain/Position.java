package erp.company.domain;

import erp.global.base.TimeStamped;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Position extends TimeStamped {
    private Long positionId;
    private String name;
    private int levelNo;
    private long companyId;

}
