package erp.company.dto.response;

import java.util.List;

public record GetCompanyListResponse<T>(
        List<GetCompanyResponse> companies,
        int page,
        // 아래 내용을 알려주면 프론트 구현이 쉬워짐
        int size,
        long totalElements, // 전체 데이터 개수
        int totalPages, // 전체 페이지 개수
        boolean hasNext
) {
}
