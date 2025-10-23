package erp.outbound.service;

import erp.global.response.PageResponse;
import erp.outbound.dto.request.OutboundFindAllRequest;
import erp.outbound.dto.request.OutboundSaveRequest;
import erp.outbound.dto.response.OutboundFindAllResponse;

public interface OutboundService {
    long saveOutbound(OutboundSaveRequest request);

    PageResponse<OutboundFindAllResponse> findAllOutbound(
            OutboundFindAllRequest request);

    void cancelOutbound(long outboundId);
}
