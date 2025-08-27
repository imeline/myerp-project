package erp.company.domain;

import erp.global.base.TimeStamped;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Department extends TimeStamped {
    private Long departmentId;
    private String name;
    private long companyId;
    private long parentId; // 상위 부서 ID
}
