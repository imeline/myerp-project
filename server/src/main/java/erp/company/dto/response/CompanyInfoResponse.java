package erp.company.dto.response;

import erp.company.domain.Company;
import lombok.Builder;

@Builder
public record CompanyInfoResponse(
        String name,
        String bizNo,
        String address,
        String phone
) {
    public static CompanyInfoResponse from(Company company) {
        return CompanyInfoResponse.builder()
                .name(company.getName())
                .bizNo(company.getBizNo())
                .address(company.getAddress())
                .phone(company.getPhone())
                .build();
    }
}
