package erp.company.dto.response;

import erp.company.domain.Company;
import lombok.Builder;

@Builder
public record CompanyItemResponse(
        String name,
        String bizNo,
        String address,
        String phone
) {
    public static CompanyItemResponse from(Company company) {
        return CompanyItemResponse.builder()
                .name(company.getName())
                .bizNo(company.getBizNo())
                .address(company.getAddress())
                .phone(company.getPhone())
                .build();
    }
}
