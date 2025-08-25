package erp.system.domain;

import java.time.LocalDateTime;

public class Log {
    private Long logId;
    private String type;
    private Boolean success;
    private String description;
    private String ipAddress;
    private Long employeeId;
    private Long companyId;
    private LocalDateTime createdAt;
}
