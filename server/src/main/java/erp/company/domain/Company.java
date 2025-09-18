package erp.company.domain;

import erp.global.base.TimeStamped;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부(new) 생성 제한
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 빌더용 내부 생성자
public class Company extends TimeStamped {
    private Long companyId; // PK는 삽입 전 null일 수 있으니, 래퍼 타입 Long 사용
    private String name;
    private String bizNo;
    private String address;
    private String phone;
    // 삭제 이외의 회원가입 등에선 삭제와 무관한 정보 이므로, mapper에서만 null 처리
    private LocalDateTime deletedAt;

    // register 이랑 update 메소드 필드가 똑같아서 of로 통일
    public static Company of(Long companyId, String name, String bizNo,
                             String address, String phone) {
        return Company.builder()
                .companyId(companyId)
                .name(name)
                .bizNo(bizNo)
                .address(address)
                .phone(phone)
                .build();
    }
}
