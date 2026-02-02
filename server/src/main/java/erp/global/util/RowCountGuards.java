package erp.global.util;

import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// 기본 생성자가 public 생성되는 거 방지
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RowCountGuards {
    // DML 결과가 정확히 1건이어야 할 때
    public static void requireOneRowAffected(int affected, ErrorStatus status) {
        if (affected != 1) {
            throw new GlobalException(status);
        }
    }

    // 1건 이상이면 OK
    public static void requireNonZeroRowsAffected(int affected, ErrorStatus status) {
        if (affected < 1) {
            throw new GlobalException(status);
        }
    }
}
