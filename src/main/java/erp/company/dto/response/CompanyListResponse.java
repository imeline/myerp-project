package erp.company.dto.response;

import erp.company.dto.internal.CompanyRow;
import lombok.Builder;

import java.util.List;

@Builder
public record CompanyListResponse<T>(
        List<CompanyRow> companies,
        int page, // 현재 페이지 번호 (0부터 시작)
        // 아래 내용을 알려주면 프론트 구현이 쉬워짐
        long totalElements, // 전체 데이터 개수
        int totalPages, // 전체 페이지 개수
        boolean hasNext
) {
    public static <T> CompanyListResponse<T> of(List<CompanyRow> companies,
                                                int page,
                                                long totalElements,
                                                int totalPages,
                                                boolean hasNext) {
        return CompanyListResponse.<T>builder()
                .companies(companies)
                .page(page)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .hasNext(hasNext)
                .build();
    }

}
