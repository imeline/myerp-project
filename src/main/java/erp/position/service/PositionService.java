package erp.position.service;

import erp.position.dto.request.PositionLevelNoRequest;
import erp.position.dto.request.PositionNameRequest;
import erp.position.dto.response.PositionFindAllResponse;

import java.util.List;

public interface PositionService {

    long savePosition(PositionNameRequest request);

    List<PositionFindAllResponse> findAllPosition();

    void updatePositionName(long positionId, PositionNameRequest request);

    void updatePositionLevelNo(long positionId, PositionLevelNoRequest request);

    void deletePosition(long positionId);

}
