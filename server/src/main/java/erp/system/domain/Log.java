package erp.system.domain;

import java.time.LocalDateTime;

public class Log {
    private Long logId;
    private String type;
    private boolean success;
    private String description;
    private String ipAddress;
    private long employeeId;
    private long companyId;
    private LocalDateTime createdAt;
}
