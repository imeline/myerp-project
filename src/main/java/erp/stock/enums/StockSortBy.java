package erp.stock.enums;

public enum StockSortBy {
    AVAILABLE,        // 가용재고(onHand - allocated)
    ON_HAND,          // 현재 재고
    ALLOCATED,        // 예약 재고
    ITEM_NAME,        // 품목명
    ITEM_CODE,        // 품목코드
    UNIT_PRICE,       // 단가
    INVENTORY_VALUE,  // 재고가치(onHand * unitPrice)
    CREATED_AT        // 생성일(최근 우선)
}
