package erp.position.dto.response;

import erp.position.domain.Position;
import lombok.Builder;

@Builder
public record GetPositionResponse(
        Long positionId,
        String name,
        Integer levelNo
) {
    public static GetPositionResponse from(Position position) {
        return GetPositionResponse.builder()
                .positionId(position.getPositionId())
                .name(position.getName())
                .levelNo(position.getLevelNo())
                .build();
    }
}
