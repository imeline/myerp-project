package erp.position.validation;

public interface PositionValidator {
    void validPositionIdIfPresent(long positionId);

    void validNameUnique(String name, Long excludeId);
}
