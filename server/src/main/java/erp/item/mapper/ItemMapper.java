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

    int save(@Param("tenantId") long tenantId,
             @Param("item") Item item);

    Optional<Item> findById(@Param("tenantId") long tenantId,
                            @Param("itemId") long itemId);

    List<ItemFindRow> findAllItemFindRow(@Param("tenantId") long tenantId,
                                         @Param("name") String name,
                                         @Param("category") ItemCategory category,
                                         @Param("offset") int offset,
                                         @Param("size") int size);

    List<ItemOptionRow> findAllItemOptionRow(@Param("tenantId") long tenantId);

    List<ItemPriceRow> findAllPriceByIds(long tenantId, List<Long> itemIds);

    int updateById(@Param("tenantId") long tenantId,
                   @Param("item") Item item);

    int softDeleteById(@Param("tenantId") long tenantId,
                       @Param("itemId") long itemId);

    long countByNameAndCategory(@Param("tenantId") long tenantId,
                                @Param("name") String name,
                                @Param("category") ItemCategory category);

    boolean existsByName(@Param("tenantId") long tenantId,
                         @Param("name") String name,
                         @Param("excludeId") Long excludeId);

    boolean existsByCode(@Param("tenantId") long tenantId,
                         @Param("code") String code,
                         @Param("excludeId") Long excludeId);

    boolean existsByIds(@Param("tenantId") long tenantId,
                        @Param("itemIds") List<Long> itemIds);

    boolean existsById(@Param("tenantId") long tenantId,
                       @Param("itemId") long itemId);
}
