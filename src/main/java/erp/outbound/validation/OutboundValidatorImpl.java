package erp.outbound.validation;

import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.outbound.mapper.OutboundMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboundValidatorImpl implements OutboundValidator {

    private final OutboundMapper outboundMapper;

    @Override
    public void validOutboundIdIfPresent(long outboundId, long tenantId) {
        if (!outboundMapper.existsById(tenantId, outboundId)) {
            throw new GlobalException(ErrorStatus.NOT_FOUND_OUTBOUND);
        }
    }
}
