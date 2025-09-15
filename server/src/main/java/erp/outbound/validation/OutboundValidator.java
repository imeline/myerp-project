package erp.outbound.validation;

public interface OutboundValidator {

    void validOutboundIdIfPresent(long outboundId, long tenantId);
}
