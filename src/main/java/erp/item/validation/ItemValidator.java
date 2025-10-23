package erp.item.validation;

import java.util.List;

public interface ItemValidator {
    void validNameUnique(String name, Long excludeId);

    void validCodeUnique(String code, Long excludeId);

    void validItemIdsIfPresent(List<Long> itemIds);

    void validItemIdIfPresent(long itemId);
}
