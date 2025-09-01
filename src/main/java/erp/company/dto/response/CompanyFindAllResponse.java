package erp.company.dto.response;

import erp.company.dto.internal.CompanyFindRow;
import lombok.Builder;

import java.util.List;

@Builder
public record CompanyFindAllResponse<T>(
        List<CompanyFindRow> companies,
        int page, // 현재 페이지 번호 (0부터 시작)
        // 아래 내용을 알려주면 프론트 구현이 쉬워짐
        long totalElements, // 전체 데이터 개수
        int totalPages, // 전체 페이지 개수
        boolean hasNext
) {
    public static <T> CompanyFindAllResponse<T> of(List<CompanyFindRow> companies,
                                                   int page,
                                                   long totalElements,
                                                   int totalPages,
                                                   boolean hasNext) {
        return CompanyFindAllResponse.<T>builder()
                .companies(companies)
                .page(page)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .hasNext(hasNext)
                .build();
    }

}
