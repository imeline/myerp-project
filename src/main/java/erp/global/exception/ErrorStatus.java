package erp.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorStatus {

    // 공통
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "잘못된 요청입니다."),
    NOT_READABLE(HttpStatus.BAD_REQUEST, "NOT_READABLE", "요청 본문을 해석할 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR",
            "서버 내부 오류가 발생했습니다."),

    // AUTH
    DUPLICATE_LOGIN_EMAIL("AUTH101", "기존 사용중인 이메일입니다."),
    INVALID_LOGIN_CREDENTIALS("AUTH102", "아이디 또는 비밀번호가 일치하지 않습니다."),
    SIGNUP_FAIL("AUTH104", "회원가입에 실패했습니다."),
    NOT_FOUND_ERP_ACCOUNT("AUTH105", "계정을 찾을 수 없습니다."),
    CREATE_REFRESH_TOKEN_FAIL(
            "AUTH106", "리프레시 토큰 생성에 실패했습니다."),
    REFRESH_TOKEN_NOT_FOUND("AUTH107", "리프레시 토큰을 찾을 수 없습니다."),
    EXPIRED_REFRESH_TOKEN("AUTH108", "만료된 리프레시 토큰입니다."),
    UPDATE_REFRESH_TOKEN_FAIL("AUTH109", "리프레시 토큰 갱신에 실패했습니다."),
    DELETE_REFRESH_TOKEN_FAIL("AUTH110", "리프레시 토큰 삭제에 실패했습니다."),
    INVALID_REFRESH_TOKEN("AUTH111", "유효하지 않은 리프레시 토큰입니다."),

    // ERP ACCOUNT
    CREATE_ERP_ACCOUNT_FAIL("ERP201", "계정 생성에 실패했습니다."),
    DELETE_ERP_ACCOUNT_FAIL("ERP202", "계정 삭제에 실패했습니다."),

    // EMPLOYEE
    NOT_REGISTERED_EMPLOYEE("EMP301", "등록된 직원이 없습니다."),
    DUPLICATE_EMP_NO("EMP302", "이미 등록된 사번입니다."),
    DUPLICATE_PHONE("EMP303", "이미 등록된 전화번호입니다."),
    CREATE_EMPLOYEE_FAIL("EMP304", "직원 생성에 실패했습니다."),
    NOT_FOUND_EMPLOYEE("EMP305", "존재하지 않는 직원입니다."),
    UPDATE_EMPLOYEE_FAIL("EMP306", "직원 수정에 실패했습니다."),
    ALREADY_RETIRED_EMPLOYEE("EMP307", "이미 퇴사 처리된 직원입니다."),

    // DEPARTMENT
    DUPLICATE_DEPARTMENT_NAME("DPT501", "이미 등록된 부서명입니다."),
    NOT_FOUND_DEPARTMENT("DPT502", "존재하지 않는 부서입니다."),
    CREATE_DEPARTMENT_FAIL("DPT503", "부서 생성에 실패했습니다."),
    UPDATE_DEPARTMENT_FAIL("DPT504", "부서 수정에 실패했습니다."),
    DELETE_DEPARTMENT_FAIL("DPT505", "부서를 찾을 수 없거나 이미 삭제된 부서입니다."),
    NO_FOUND_PARENT_DEPARTMENT("DPT506", "유효하지 않은 상위 부서입니다."), EXIST_EMPLOYEE_IN_DEPARTMENT(
            "DPT507", "해당 부서에 속한 직원이 존재합니다."),
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
    NOT_FOUND_ITEM_PRICE("ITEM709", "품목에 대한 가격 정보가 없습니다."),
    CANNOT_DELETE_ITEM_STOCK_NOT_ZERO("ITEM710", "재고 수량이 0이 아닌 품목은 삭제할 수 없습니다."),

    // STOCK
    INVALID_STOCK_QUANTITY("STK801", "재고 수량은 0 이상이어야 합니다."),
    CREATE_STOCK_FAIL("STK802", "재고 생성에 실패했습니다."),
    NOT_REGISTERED_STOCK("STK803", "등록된 재고가 없습니다."),
    NOT_FOUND_STOCK("STK804", "존재하지 않는 재고입니다."),
    DECREASE_STOCK_ALLOCATED_FAIL("STK806", "예약 재고 감소에 실패했습니다."),
    INCREASE_STOCK_ALLOCATED_FAIL("STK807", "예약 재고 증가에 실패했습니다."),

    // PURCHASE
    CREATE_PURCHASE_FAIL("PUR901", "발주 생성에 실패했습니다."),
    CREATE_PURCHASE_ITEM_FAIL("PUR902", "발주 품목 생성에 실패했습니다."),
    NOT_FOUND_PURCHASE("PUR903", "존재하지 않는 발주입니다."),
    INVALID_PURCHASE_STATUS("PUR904", "유효하지 않은 발주 상태입니다."),
    NOT_REGISTERED_PURCHASE("PUR905", "등록된 발주가 없습니다."),
    NOT_FOUND_PURCHASE_ITEM("PUR906", "존재하지 않는 발주 품목입니다."),
    CANNOT_CANCEL_SHIPPED_PURCHASE("PUR907", "입고된 발주는 취소할 수 없습니다."),
    CANCEL_PURCHASE_FAIL("PUR908", "발주 취소에 실패했습니다."),
    NOT_REGISTERED_PURCHASE_ITEM("PUR909", "등록된 발주 품목이 없습니다."),
    CANNOT_SHIP_CANCELLED_PURCHASE("PUR910", "취소된 발주는 입고 처리할 수 없습니다."),
    ALREADY_SHIPPED_PURCHASE("PUR911", "이미 입고 처리된 발주입니다."),
    UPDATE_PURCHASE_STATUS_FAIL("PUR912", "발주 상태 변경에 실패했습니다."),
    CANNOT_REVERT_CANCELLED_PURCHASE("PUR913", "취소된 발주는 상태를 되돌릴 수 없습니다."),
    ALREADY_CONFIRMED_PURCHASE("PUR914", "이미 확정 상태의 발주입니다."),

    // INBOUND
    CREATE_INBOUND_FAIL("INB1001", "입고 생성에 실패했습니다."),
    NOT_REGISTERED_INBOUND("INB1002", "등록된 입고가 없습니다."),
    NOT_FOUND_INBOUND("INB1003", "존재하지 않는 입고입니다."),
    INBOUND_NOT_ACTIVE("INB1004", "활성 상태가 아닌 입고입니다."),
    CANCEL_INBOUND_FAIL("INB1005", "입고 취소에 실패했습니다."),

    // ORDER
    CREATE_ORDER_FAIL("ORD1101", "주문 생성에 실패했습니다."),
    CREATE_ORDER_ITEM_FAIL("ORD1102", "주문 품목 생성에 실패했습니다."),
    NOT_REGISTERED_ORDER("ORD1103", "등록된 주문이 없습니다."),
    NOT_FOUND_ORDER_ITEM("ORD1104", "존재하지 않는 주문 품목입니다."),
    NOT_FOUND_ORDER("ORD1105", "존재하지 않는 주문입니다."),
    CANNOT_CANCEL_SHIPPED_ORDER("ORD1106", "출고된 주문은 취소할 수 없습니다."),
    CANCEL_ORDER_FAIL("ORD1107", "주문 취소에 실패했습니다."),
    ALREADY_CANCELLED_ORDER("ORD1108", "이미 취소된 주문입니다."),
    CANNOT_SHIP_CANCELLED_ORDER("ORD1109", "취소된 주문은 출고 처리할 수 없습니다."),
    ALREADY_SHIPPED_ORDER("ORD1110", "이미 출고 처리된 주문입니다."),
    UPDATE_ORDER_STATUS_FAIL("ORD1111", "주문 상태 변경에 실패했습니다."),
    CANNOT_REVERT_CANCELLED_ORDER("ORD1112", "취소된 주문은 상태를 되돌릴 수 없습니다."), ALREADY_CONFIRMED_ORDER("ORD1113", "이미 확정 상태의 주문입니다."),

    // OUTBOUND
    CREATE_OUTBOUND_FAIL("OUT1201", "출고 생성에 실패했습니다."),
    NOT_REGISTERED_OUTBOUND("OUT1202", "등록된 출고가 없습니다."),
    UPDATE_OUTBOUND_STAUS_FAIL("OUT1203", "출고 상태 변경에 실패했습니다."),
    NOT_FOUND_OUTBOUND("OUT1204", "존재하지 않는 출고입니다."),
    CANNOT_DELETE_ITEM_BY_CONFIRMED_PURCHASE("ITEM711", "열린 상태 발주에서 참조된 품목은 삭제할 수 없습니다."),
    CANNOT_DELETE_ITEM_BY_CONFIRMED_ORDER("ITEM712", "열린 상태 주문에서 참조된 품목은 삭제할 수 없습니다."),

    // LOG
    LOG_SAVE_FAIL("LOG1301", "로그 저장에 실패했습니다."),
    LOG_PAYLOAD_INVALID_JSON("LOG1302", "로그 페이로드가 유효한 JSON 형식이 아닙니다."),
    ;


    private final HttpStatus httpStatus;
    private final String status;
    private final String message;

    ErrorStatus(String status, String message) {
        this.httpStatus = HttpStatus.BAD_REQUEST; // 커스텀 에러의 상태 코드 기본값
        this.status = status;
        this.message = message;
    }

    ErrorStatus(HttpStatus httpStatus, String status, String message) {
        this.httpStatus = httpStatus;
        this.status = status;
        this.message = message;
    }
}
