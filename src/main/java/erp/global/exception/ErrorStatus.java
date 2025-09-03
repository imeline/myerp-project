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

    // AUTH
    DUPLICATE_LOGIN_EMAIL("AUTH101", "기존 사용중인 이메일입니다."),
    INVALID_LOGIN_CREDENTIALS("AUTH102", "아이디 또는 비밀번호가 일치하지 않습니다."), NOT_FOUND_TENANT_ID("AUTH103", "테넌트 ID가 설정되지 않았습니다."),
    SIGNUP_FAIL("AUTH104", "회원가입에 실패했습니다."),
    NOT_FOUND_ERP_ACCOUNT("AUTH105", "계정을 찾을 수 없습니다."),

    // ERP ACCOUNT
    CREATE_ERP_ACCOUNT_FAIL("ERP201", "계정 생성에 실패했습니다."),

    // EMPLOYEE
    NOT_REGISTERED_EMPLOYEE("EMP301", "등록된 직원이 없습니다."),
    DUPLICATE_EMP_NO("EMP302", "이미 등록된 사번입니다."),
    DUPLICATE_PHONE("EMP303", "이미 등록된 전화번호입니다."),
    CREATE_EMPLOYEE_FAIL("EMP304", "직원 생성에 실패했습니다."),
    EMPLOYEE_NOT_FOUND("EMP305", "존재하지 않는 직원입니다."),

    // COMPANY
    DUPLICATE_BIZ_NO("COMP401", "이미 등록된 사업자등록번호입니다."),
    DUPLICATE_NAME("COMP402", "이미 등록된 회사명입니다."),
    NOT_FOUND_COMPANY("COMP403", "존재하지 않는 회사입니다."),
    CREATE_COMPANY_FAIL("COMP404", "회사 생성에 실패했습니다."),
    UPDATE_COMPANY_FAIL("COMP405", "회사 수정에 실패했습니다."),
    DELETE_COMPANY_FAIL("COMP406", "회사를 찾을 수 없거나 이미 삭제된 회사입니다."),
    EXTERNAL_DATA_EXISTS("COMP407", "연관된 데이터가 존재하여 삭제할 수 없습니다."),
    NOT_REGISTERED_COMPANY("COMP408", "등록된 회사가 없습니다."),

    // DEPARTMENT
    DUPLICATE_DEPARTMENT_NAME("DPT501", "이미 등록된 부서명입니다."),
    NOT_FOUND_DEPARTMENT("DPT502", "존재하지 않는 부서입니다."),
    CREATE_DEPARTMENT_FAIL("DPT503", "부서 생성에 실패했습니다."),
    UPDATE_DEPARTMENT_FAIL("DPT504", "부서 수정에 실패했습니다."),
    DELETE_DEPARTMENT_FAIL("DPT505", "부서를 찾을 수 없거나 이미 삭제된 부서입니다."),
    NO_FOUND_PARENT_DEPARTMENT("DPT506", "유효하지 않은 상위 부서입니다."), EXIST_EMPLOYEE_IN_DEPARTMENT("DPT507", "해당 부서에 속한 직원이 존재합니다."),
    EXIST_CHILD_DEPARTMENT("DPT508", "하위 부서가 존재하여 삭제할 수 없습니다."),
    NOT_REGISTERED_DEPARTMENT("DPT509", "등록된 부서가 없습니다."),

    // POSITION
    DUPLICATE_POSITION_NAME("POS601", "이미 등록된 직급명입니다."),
    NOT_FOUND_POSITION("POS602", "존재하지 않는 직급입니다."),
    CREATE_POSITION_FAIL("POS603", "직급 생성에 실패했습니다."),
    UPDATE_POSITION_FAIL("POS604", "직급 수정에 실패했습니다."),
    DELETE_POSITION_FAIL("POS605", "직급을 찾을 수 없거나 이미 삭제된 직급입니다."),
    EXIST_EMPLOYEE_IN_POSITION("POS606", "해당 직급에 속한 직원이 존재합니다."),
    NOT_REGISTERED_POSITION("POS607", "등록된 직급이 없습니다."),

    // ITEM
    CREATE_ITEM_FAIL("ITEM701", "품목 생성에 실패했습니다."),
    NOT_FOUND_ITEM("ITEM702", "존재하지 않는 품목입니다."),
    UPDATE_ITEM_FAIL("ITEM703", "품목 수정에 실패했습니다."),
    DELETE_ITEM_FAIL("ITEM704", "품목을 찾을 수 없거나 이미 삭제된 품목입니다."),
    DUPLICATE_ITEM_NAME("ITEM705", "이미 등록된 품목명입니다."),
    DUPLICATE_ITEM_CODE("ITEM706", "이미 등록된 품목 코드입니다."),
    NOT_REGISTERED_ITEM("ITEM707", "등록된 품목이 없습니다."),
    DUPLICATE_ITEM("ITEM708", "중복된 품목이 있습니다."),
    ;


    private final String status;
    private final String message;
}
