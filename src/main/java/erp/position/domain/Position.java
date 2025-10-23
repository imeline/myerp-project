package erp.position.domain;

import erp.global.base.TimeStamped;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Position extends TimeStamped {
    private Long positionId;
    private String name;
    private int levelNo;

    public static Position register(Long positionId, String name, int levelNo) {
        return Position.builder()
                .positionId(positionId)
                .name(name)
                .levelNo(levelNo)
                .build();
    }
}
