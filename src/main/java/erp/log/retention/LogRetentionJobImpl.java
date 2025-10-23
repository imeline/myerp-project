package erp.log.retention;

import erp.log.mapper.LogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogRetentionJobImpl implements LogRetentionJob {

    private final LogMapper logMapper;

    // 매일 새벽 3시 실행
    @Scheduled(cron = "0 0 3 * * *", zone = "Asia/Seoul")
    public void run() {
        logMapper.deleteOlderThan("ACCESS", 90); // 접근 로그는 90일 보관
        logMapper.deleteOlderThan("LOGIN", 365); // 로그인 로그는 1년 보관
        logMapper.deleteOlderThan("WORK", 365);  // 작업 로그는 1년 보관
        logMapper.deleteOlderThan("ERROR", 365); // 오류 로그는 1년 보관
    }
}