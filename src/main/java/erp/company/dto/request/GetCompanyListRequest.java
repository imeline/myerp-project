package erp.company.dto.request;

import jakarta.validation.constraints.Min;

public record GetCompanyListRequest(
        String name,
        @Min(0)
        int page,
        @Min(1)
        int size
) {
}
