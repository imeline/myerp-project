package erp.inbound.service;

import erp.employee.mapper.EmployeeMapper;
import erp.inbound.mapper.InboundMapper;
import erp.purchase.mapper.PurchaseItemMapper;
import erp.purchase.mapper.PurchaseMapper;
import erp.stock.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InboundServiceImpl {


    private final InboundMapper inboundMapper;
    private final PurchaseMapper purchaseMapper;
    private final PurchaseItemMapper purchaseItemMapper;
    private final EmployeeMapper employeeMapper;
    private final StockMapper stockMapper;

    private static final String DEFAULT_WAREHOUSE = "MAIN";

}
