package erp.item.validation;

import java.util.List;

public interface ItemValidator {
    void validNameUnique(String name, Long excludeId, long tenantId);

    void validCodeUnique(String code, Long excludeId, long tenantId);

    void validItemIdsExist(List<Long> itemIds, long tenantId);
}
