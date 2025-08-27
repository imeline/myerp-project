package erp.position.domain;

import erp.global.base.TimeStamped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Position extends TimeStamped {

    private Long positionId;
    private String name;
    private int levelNo;
    private long companyId;

    public static Position of(Long positionId, String name, int levelNo, long companyId) {
        return Position.builder()
            .positionId(positionId)
            .name(name)
            .levelNo(levelNo)
            .companyId(companyId)
            .build();
    }

}
