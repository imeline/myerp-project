package erp.purchase.mapper;

import erp.purchase.domain.Purchase;
import erp.purchase.dto.internal.PurchaseCodeAndSupplierRow;
import erp.purchase.dto.internal.PurchaseDetailRow;
import erp.purchase.dto.internal.PurchaseFindRow;
import erp.purchase.enums.PurchaseStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Mapper
public interface PurchaseMapper {
    long nextId();

    int save(@Param("purchase") Purchase purchase);

    List<PurchaseFindRow> findAllPurchaseFindRow(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("code") String code,
            @Param("status") PurchaseStatus status,
            @Param("offset") int offset,
            @Param("size") int size
    );

    List<PurchaseCodeAndSupplierRow> findAllCodeAndSupplier(

    );

    Optional<PurchaseDetailRow> findPurchaseDetailRow(
            @Param("purchaseId") long purchaseId);

    Optional<PurchaseStatus> findStatusById(@Param("purchaseId") long purchaseId);

    int updateStatusToIfConfirmed(@Param("purchaseId") long purchaseId,
                                  @Param("to") PurchaseStatus to);

    // shipped 는 canceled 될 수 없다.
    // UI 상으로 shipped 상태에서 취소는 confirmed
    // 그 후에 confirmed 상태에서 canceled로 변경 가능
    int updateStatusToConfirmedIfShipped(@Param("purchaseId") long purchaseId);

    long countByPeriodAndCodeAndStatus(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("code") String code,
            @Param("status") PurchaseStatus status
    );

    boolean existsById(@Param("purchaseId") long purchaseId);

    boolean existsShippedById(@Param("purchaseId") long purchaseId);

    boolean validConfirmIfPresent(@Param("itemId") long itemId);

}
