package erp.position.service;

import erp.position.dto.request.PositionLevelNoRequest;
import erp.position.dto.request.PositionNameRequest;
import erp.position.dto.response.PositionFindAllResponse;

import java.util.List;

public interface PositionService {

    long savePosition(PositionNameRequest request, long tenantId);

    List<PositionFindAllResponse> findAllPosition(long tenantId);

    void updatePositionName(long positionId, PositionNameRequest request, long tenantId);

    void updatePositionLevelNo(long positionId, PositionLevelNoRequest request,
                               long tenantId);

    void deletePosition(long positionId, long tenantId);

}
