package erp.global.base;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class TimeStamped {
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}


