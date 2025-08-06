package erp.employee.domain;

import erp.global.base.TimeStamped;

public class Employee extends TimeStamped {
    private Long employeeId;
    private Long companyId;
    private String empNo;
    private String name;
    private String department;
    private String position;
    private String phone;
    private Boolean erpEnabled;
}
