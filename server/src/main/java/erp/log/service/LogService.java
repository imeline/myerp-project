package erp.log.service;

import erp.log.dto.request.LogSaveRequest;

public interface LogService {
    void save(LogSaveRequest request);
}
