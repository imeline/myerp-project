package erp.department.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddDepartmentsRequest(
        @NotBlank
        String key,        // 요청 내 유일
        String parentKey,  // 같은 요청 안 부모를 임시키로 지정(옵션)
        Long parentId,     // 기존 부모ID 지정(옵션) — parentKey와 동시 사용 금지
        @NotBlank
        String name
) {
}
