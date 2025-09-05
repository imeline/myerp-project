package erp.item.validation;

import java.util.List;

public interface ItemValidator {
    void validNameUnique(String name, Long excludeItemId, long tenantId);

    void validCodeUnique(String code, Long excludeItemId, long tenantId);

    void validItemIdsExist(List<Long> itemIds, long tenantId);
}
