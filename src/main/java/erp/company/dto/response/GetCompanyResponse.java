package erp.company.dto.response;

import java.time.LocalDateTime;

public record GetCompanyResponse(
        long companyId,
        String name,
        String bizNo,
        String address,
        String phone,
        LocalDateTime createdAt
) {
}
