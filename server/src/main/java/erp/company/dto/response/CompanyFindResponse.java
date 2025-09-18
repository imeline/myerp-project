package erp.company.dto.response;

import erp.company.dto.internal.CompanyFindRow;

public record CompanyFindResponse(
        Long companyId,
        String name,
        String bizNo,
        String address,
        String phone,
        String createdAt
) {
    public static CompanyFindResponse from(CompanyFindRow row) {
        return new CompanyFindResponse(
                row.companyId(),
                row.name(),
                row.bizNo(),
                row.address(),
                row.phone(),
                row.createdAt()
        );
    }
}


