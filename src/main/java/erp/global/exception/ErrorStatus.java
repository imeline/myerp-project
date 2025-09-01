package erp.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {

    // 공통
    BAD_REQUEST("BAD_REQUEST_400", "잘못된 요청입니다."),
    NOT_READABLE("NOT_READABLE_400", "요청 본문을 해석할 수 없습니다."),
    UNAUTHORIZED("UNAUTHORIZED_401", "인증이 필요합니다."),
    FORBIDDEN("FORBIDDEN_403", "접근 권한이 없습니다."),
    NOT_FOUND("NOT_FOUND_404", "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR_500",
            "서버 내부 오류가 발생했습니다."),
    NULL_TENANT_ID("TENANT001", "테넌트 ID가 설정되지 않았습니다."),

    // AUTH,
    EXIST_LOGIN_EMAIL("AUTH101", "기존 사용중인 이메일입니다."),
    INVALID_LOGIN_CREDENTIALS("AUTH102", "아이디 또는 비밀번호가 일치하지 않습니다."), NOT_FOUND_TENANT_ID("AUTH103", "테넌트 ID가 설정되지 않았습니다."),
    SIGNUP_FAIL("AUTH104", "회원가입에 실패했습니다."),

    // COMPANY,
    DUPLICATE_BIZ_NO("COMP101", "이미 등록된 사업자등록번호입니다."),
    DUPLICATE_NAME("COMP102", "이미 등록된 회사명입니다."),
    NOT_FOUND_COMPANY("COMP103", "존재하지 않는 회사입니다."),
    CREATE_COMPANY_FAIL("COMP104", "회사 등록에 실패했습니다."),
    UPDATE_COMPANY_FAIL("COMP105", "회사 수정에 실패했습니다."),
    DELETE_COMPANY_FAIL("COMP106", "회사를 찾을 수 없거나 이미 삭제된 회사입니다."),
    EXTERNAL_DATA_EXISTS("COMP107", "연관된 데이터가 존재하여 삭제할 수 없습니다."),
    NOT_REGISTERED_COMPANY("COMP209", "등록된 회사가 없습니다."),

    // DEPARTMENT
    DUPLICATE_DEPARTMENT_NAME("DPT201", "이미 등록된 부서명입니다."),
    NOT_FOUND_DEPARTMENT("DPT202", "존재하지 않는 부서입니다."),
    CREATE_DEPARTMENT_FAIL("DPT203", "부서 등록에 실패했습니다."),
    UPDATE_DEPARTMENT_FAIL("DPT204", "부서 수정에 실패했습니다."),
    DELETE_DEPARTMENT_FAIL("DPT205", "부서를 찾을 수 없거나 이미 삭제된 부서입니다."),
    NO_FOUND_PARENT_DEPARTMENT("DPT206", "유효하지 않은 상위 부서입니다."), EXIST_EMPLOYEE_IN_DEPARTMENT("DPT207", "해당 부서에 속한 직원이 존재하여 삭제할 수 없습니다."),
    EXIST_CHILD_DEPARTMENT("DPT208", "하위 부서가 존재하여 삭제할 수 없습니다."),
    NOT_REGISTERED_DEPARTMENT("DPT209", "등록된 부서가 없습니다."),

    ;

    private final String status;
    private final String message;
}
