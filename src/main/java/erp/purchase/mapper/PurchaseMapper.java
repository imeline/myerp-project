package erp.purchase.mapper;

import erp.purchase.domain.Purchase;
import erp.purchase.dto.internal.PurchaseCodeAndSupplierRow;
import erp.purchase.dto.internal.PurchaseFindRow;
import erp.purchase.enums.PurchaseStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface PurchaseMapper {
    long nextId();

    int save(@Param("tenantId") long tenantId, @Param("purchase") Purchase purchase);

    List<PurchaseFindRow> findAllPurchaseFindRow(
            @Param("tenantId") long tenantId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("code") String code,
            @Param("status") PurchaseStatus status,
            @Param("offset") int offset,
            @Param("size") int size
    );

    List<PurchaseCodeAndSupplierRow> findAllCodeAndSupplier(
            @Param("tenantId") long tenantId
    );

    // SHIPPED 가 아닌 경우에만 CANCELLED 로 변경 (조건부 업데이트)
    int updateStatusToCancelledIfNotShipped(@Param("tenantId") long tenantId,
                                            @Param("purchaseId") long purchaseId);

    long countByPeriodAndCodeAndStatus(
            @Param("tenantId") long tenantId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("code") String code,
            @Param("status") PurchaseStatus status
    );

    boolean existsById(@Param("tenantId") long tenantId,
                       @Param("purchaseId") long purchaseId);

    boolean existsShippedById(@Param("tenantId") long tenantId,
                              @Param("purchaseId") long purchaseId);
}
