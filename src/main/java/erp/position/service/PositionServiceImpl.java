package erp.position.service;

import erp.employee.mapper.EmployeeMapper;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.position.domain.Position;
import erp.position.dto.request.PositionLevelNoRequest;
import erp.position.dto.request.PositionNameRequest;
import erp.position.dto.response.PositionFindAllResponse;
import erp.position.mapper.PositionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionMapper positionMapper;
    private final EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public long savePosition(PositionNameRequest request, long tenantId) {
        long newPositionId = positionMapper.nextId();

        String name = request.name();
        validNameUnique(name, null, tenantId);

        // 직급 번호는 가장 마지막 직급 번호 + 1
        int levelNo = positionMapper.findLastLevelNo(tenantId) + 1;

        Position position = Position.of(newPositionId, name, levelNo, tenantId);
        int affectedRowCount = positionMapper.save(tenantId, position);
        assertAffected(affectedRowCount, ErrorStatus.CREATE_POSITION_FAIL);

        return newPositionId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PositionFindAllResponse> findAllPosition(long tenantId) {
        List<PositionFindAllResponse> list = positionMapper.findAll(tenantId).stream()
                .map(PositionFindAllResponse::from)
                .toList();
        if (list.isEmpty()) {
            throw new GlobalException(ErrorStatus.NOT_REGISTERED_POSITION);
        }
        return list;
    }

    @Override
    @Transactional
    public void updatePositionName(long positionId, PositionNameRequest request, long tenantId) {
        String newName = request.name();
        validNameUnique(newName, positionId, tenantId);

        int affectedRowCount = positionMapper.updateNameById(tenantId, positionId, newName);
        assertAffected(affectedRowCount, ErrorStatus.UPDATE_POSITION_FAIL);
    }

    @Override
    @Transactional
    public void updatePositionLevelNo(long positionId, PositionLevelNoRequest request, long tenantId) {
        int newLevelNo = request.levelNo();
        int oldLevelNo = positionMapper.findLevelNoById(tenantId, positionId)
                .orElseThrow(() -> new GlobalException(ErrorStatus.NOT_FOUND_POSITION));

        if (newLevelNo < oldLevelNo)
            positionMapper.shiftUpRange(tenantId, newLevelNo, oldLevelNo);
        else if (newLevelNo > oldLevelNo)
            positionMapper.shiftDownRange(tenantId, oldLevelNo, newLevelNo);

        int affectedRowCount = positionMapper.updateLevelNoById(tenantId, positionId, newLevelNo);
        assertAffected(affectedRowCount, ErrorStatus.UPDATE_POSITION_FAIL);
    }

    @Override
    @Transactional
    public void deletePosition(long positionId, long tenantId) {
        validEmployeeInPositionIfPresent(positionId, tenantId);
        // 삭제하려는 직급의 levelNo
        int oldLevelNo = positionMapper.findLevelNoById(tenantId, positionId)
                .orElseThrow(() -> new GlobalException(ErrorStatus.NOT_FOUND_POSITION));
        // 직급 삭제
        int affectedRowCount = positionMapper.deleteById(tenantId, positionId);
        assertAffected(affectedRowCount, ErrorStatus.DELETE_POSITION_FAIL);
        // 삭제된 직급보다 낮은 직급들의 levelNo -1
        positionMapper.shiftDownRange(tenantId, oldLevelNo, Integer.MAX_VALUE);
    }

    private void validNameUnique(String name, Long excludePositionId, Long tenantId) {
        if (positionMapper.existsByName(tenantId, name, excludePositionId)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_POSITION_NAME);
        }
    }

    // 해당 직급에 속한 사원이 있는지 검사
    private void validEmployeeInPositionIfPresent(long positionId, long tenantId) {
        if (employeeMapper.existsByPositionId(tenantId, positionId)) {
            throw new GlobalException(ErrorStatus.EXIST_EMPLOYEE_IN_POSITION);
        }
    }

    private void assertAffected(int affected, ErrorStatus status) {
        if (affected != 1) {
            throw new GlobalException(status);
        }
    }
}
