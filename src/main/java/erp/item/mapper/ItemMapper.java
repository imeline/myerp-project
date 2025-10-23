package erp.item.mapper;

import erp.item.domain.Item;
import erp.item.dto.internal.ItemFindRow;
import erp.item.dto.internal.ItemOptionRow;
import erp.item.dto.internal.ItemPriceRow;
import erp.item.enums.ItemCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ItemMapper {
    long nextId();

    int save(@Param("item") Item item);

    Optional<Item> findById(@Param("itemId") long itemId);

    List<ItemFindRow> findAllItemFindRow(@Param("name") String name,
                                         @Param("category") ItemCategory category,
                                         @Param("offset") int offset,
                                         @Param("size") int size);

    List<ItemOptionRow> findAllItemOptionRow();

    List<ItemPriceRow> findAllPriceByIds(@Param("itemIds") List<Long> itemIds);

    int updateById(@Param("item") Item item);

    int softDeleteById(@Param("itemId") long itemId);

    long countByNameAndCategory(@Param("name") String name,
                                @Param("category") ItemCategory category);

    boolean existsByName(@Param("name") String name,
                         @Param("excludeId") Long excludeId);

    boolean existsByCode(@Param("code") String code,
                         @Param("excludeId") Long excludeId);

    boolean existsByIds(@Param("itemIds") List<Long> itemIds);

    boolean existsById(@Param("itemId") long itemId);
}
