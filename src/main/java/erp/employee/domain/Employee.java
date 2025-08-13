package erp.employee.domain;

import erp.global.base.TimeStamped;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Employee extends TimeStamped {
    private Long employeeId;
    private String empNo;
    private String name;
    private String department;
    private String position;
    private String phone;
    private Long companyId;

    public static Employee register(Long employeeId, Long companyId,
                                    String empNo, String name,
                                    String department, String position,
                                    String phone) {
        return Employee.builder()
                .employeeId(employeeId)
                .companyId(companyId)
                .empNo(empNo)
                .name(name)
                .department(department)
                .position(position)
                .phone(phone)
                .build();
    }
}
