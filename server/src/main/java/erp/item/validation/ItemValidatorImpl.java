package erp.item.validation;

import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.item.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemValidatorImpl implements ItemValidator {

    private final ItemMapper itemMapper;

    @Override
    public void validNameUnique(String name, Long excludeId, long tenantId) {
        if (name != null && itemMapper.existsByName(tenantId, name, excludeId)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_ITEM_NAME);
        }
    }

    @Override
    public void validCodeUnique(String code, Long excludeId, long tenantId) {
        if (code != null && itemMapper.existsByCode(tenantId, code, excludeId)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_ITEM_CODE);
        }
    }

    @Override
    public void validItemIdsIfPresent(List<Long> itemIds, long tenantId) {
        if (!itemMapper.existsByIds(tenantId, itemIds)) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_ITEM);
        }
    }

    @Override
    public void validItemIdIfPresent(long itemId, long tenantId) {
        if (!itemMapper.existsById(tenantId, itemId)) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_ITEM);
        }
    }
}
