package erp.company.dto.internal;

public record CompanyRow(
        long companyId,
        String name,
        String bizNo,
        String address,
        String phone,
        String createdAt
) {
}
