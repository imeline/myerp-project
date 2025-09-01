package erp.company.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// CompanyFindAllResponse의 companies 필드에 들어가는 요소로 쓰기 위해 만듦
// -> 도메인을 dto에서 쓰지 않기 위해
public record CompanyFindRow(
        @NotNull
        Long companyId,
        @NotBlank
        String name,
        @NotBlank
        String bizNo,
        @NotBlank
        String address,
        @NotBlank
        String phone,
        @NotBlank
        String createdAt
) {
}
