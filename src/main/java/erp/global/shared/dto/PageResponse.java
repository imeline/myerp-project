package erp.global.shared.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> rows,
        int page, // 현재 페이지 번호 (0부터 시작)
        // 아래 내용을 알려주면 프론트 구현이 쉬워짐
        long totalElements, // 전체 데이터 개수
        int totalPages, // 전체 페이지 개수
        boolean hasNext
) {
    public static <T> PageResponse<T> of(List<T> rows, int page, long total, int size) {
        int totalPages = (int) Math.ceil(total / (double) size);
        boolean hasNext = (page + 1) < totalPages;
        return new PageResponse<>(rows, page, total, totalPages, hasNext);
    }
}
