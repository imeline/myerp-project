package erp.log.retention;

import erp.log.mapper.LogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogRetentionJobImpl implements LogRetentionJob {

    private final LogMapper logMapper;

    // 매일 새벽 3시
    @Scheduled(cron = "0 0 3 * * *", zone = "Asia/Seoul")
    public void run() {
        logMapper.deleteOlderThan("ACCESS", 90);
        logMapper.deleteOlderThan("LOGIN", 365);
        logMapper.deleteOlderThan("WORK", 365);
        logMapper.deleteOlderThan("ERROR", 365);
    }
}