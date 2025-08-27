package erp.department.domain;

import erp.global.base.TimeStamped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Department extends TimeStamped {

    private Long departmentId;
    private String name;
    private long companyId;
    private Long parentId; // 상위 부서 ID

    public static Department of(Long departmentId, String name, long companyId, Long parentId) {
        return Department.builder()
            .departmentId(departmentId)
            .name(name)
            .companyId(companyId)
            .parentId(parentId)
            .build();
    }
}
