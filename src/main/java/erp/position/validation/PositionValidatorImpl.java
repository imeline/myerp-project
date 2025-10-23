package erp.position.validation;

import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.position.mapper.PositionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PositionValidatorImpl implements PositionValidator {

    private final PositionMapper positionMapper;

    @Override
    public void validPositionIdIfPresent(long positionId) {
        if (positionMapper.findLevelNoById(positionId).isEmpty()) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_POSITION);
        }
    }

    @Override
    public void validNameUnique(String name, Long excludeId) {
        if (name != null && positionMapper.existsByName(name, excludeId)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_POSITION_NAME);
        }
    }
}
