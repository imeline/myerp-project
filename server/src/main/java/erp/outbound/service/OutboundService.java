package erp.outbound.service;

import erp.outbound.dto.request.OutboundSaveRequest;

public interface OutboundService {
    long saveOutbound(OutboundSaveRequest request, long tenantId);
}
