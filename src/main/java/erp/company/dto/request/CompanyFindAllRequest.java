package erp.company.dto.request;

import jakarta.validation.constraints.Min;

public record CompanyFindAllRequest(
        String name,
        @Min(0)
        Integer page,
        @Min(1)
        Integer size
) {
}
