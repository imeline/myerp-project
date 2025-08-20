package erp.company.domain;

import erp.global.base.TimeStamped;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부(new) 생성 제한
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 빌더용 내부 생성자
public class Company extends TimeStamped {
    private Long companyId;
    private String name;
    private String bizNo;
    private String address;
    private String phone;

    public static Company register(String name, String bizNo, String address,
                                   String phone) {
        return Company.builder()
                .name(name)
                .bizNo(bizNo)
                .address(address)
                .phone(phone)
                .build();
    }

    public static Company modify(long companyId, String name, String bizNo,
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
