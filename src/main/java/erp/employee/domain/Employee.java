package erp.employee.domain;

import erp.employee.enums.EmployeeStatus;
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
    private String phone;
    private EmployeeStatus status;
    private Long departmentId;
    private Long positionId;

    public static Employee register(Long employeeId,
                                    String empNo, String name, String phone,
                                    EmployeeStatus status, long departmentId,
                                    long positionId
    ) {
        return Employee.builder()
                .employeeId(employeeId)
                .empNo(empNo)
                .name(name)
                .phone(phone)
                .status(status)
                .departmentId(departmentId)
                .positionId(positionId)
                .build();
    }

    public static Employee update(Long employeeId,
                                  String empNo,
                                  String name,
                                  String phone,
                                  Long departmentId,
                                  Long positionId) {
        return Employee.builder()
                .employeeId(employeeId)
                .name(name)
                .empNo(empNo)
                .departmentId(departmentId)
                .positionId(positionId)
                .phone(phone)
                .build();
    }
}
