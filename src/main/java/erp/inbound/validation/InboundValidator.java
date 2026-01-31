package erp.inbound.validation;

public interface InboundValidator {

    /**
     * 존재/삭제 제외 검증
     */
    void validInboundIdIfPresent(long inboundId, long tenantId);

    /**
     * 취소 가능 상태 검증: ACTIVE 가 아니면 예외
     */
    void validInboundActive(long inboundId, long tenantId);
}
