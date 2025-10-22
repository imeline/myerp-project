package erp.position.dto.response;

import erp.position.domain.Position;
import lombok.Builder;

@Builder
public record PositionFindAllResponse(
        Long positionId,
        String name,
        Integer levelNo
) {
    public static PositionFindAllResponse from(Position position) {
        return PositionFindAllResponse.builder()
                .positionId(position.getPositionId())
                .name(position.getName())
                .levelNo(position.getLevelNo())
                .build();
    }
}
