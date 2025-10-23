package erp.inbound.service;

import erp.global.response.PageResponse;
import erp.inbound.dto.request.InboundFindAllRequest;
import erp.inbound.dto.request.InboundSaveRequest;
import erp.inbound.dto.response.InboundFindAllResponse;

public interface InboundService {

    long saveInbound(InboundSaveRequest request);

    PageResponse<InboundFindAllResponse> findAllInbound(
            InboundFindAllRequest request
    );

    void cancelInbound(long inboundId);
}
