package erp.inbound.dto.response;

import erp.inbound.dto.internal.InboundFindAllRow;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record InboundFindAllResponse(
        long inboundId,
        String inboundCode,
        LocalDate inboundDate,
        String employeeName,
        long purchaseId,
        String purchaseCode,
        int totalQuantity,
        int totalAmount
) {
    public static InboundFindAllResponse from(InboundFindAllRow row) {
        return InboundFindAllResponse.builder()
                .inboundId(row.inboundId())
                .inboundCode(row.inboundCode())
                .inboundDate(row.inboundDate())
                .employeeName(row.employeeName())
                .purchaseId(row.purchaseId())
                .purchaseCode(row.purchaseCode())
                .totalQuantity(row.totalQuantity())
                .totalAmount(row.totalAmount())
                .build();
    }
}