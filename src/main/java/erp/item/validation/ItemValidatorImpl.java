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
    public void validNameUnique(String name, Long excludeItemId, long tenantId) {
        if (name != null && itemMapper.existsByName(tenantId, name, excludeItemId)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_ITEM_NAME);
        }
    }

    @Override
    public void validCodeUnique(String code, Long excludeItemId, long tenantId) {
        if (code != null && itemMapper.existsByCode(tenantId, code, excludeItemId)) {
            throw new GlobalException(ErrorStatus.DUPLICATE_ITEM_CODE);
        }
    }

    @Override
    public void validItemIdsExist(List<Long> itemIds, long tenantId) {
        if (!itemMapper.existsByIds(tenantId, itemIds)) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_ITEM);
        }
    }
}
