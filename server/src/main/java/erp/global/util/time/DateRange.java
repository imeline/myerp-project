package erp.global.util.time;

import java.time.LocalDate;

public record DateRange(
        LocalDate startDate,
        LocalDate endDate
) {
    public static DateRange of(LocalDate start, LocalDate end) {
        return new DateRange(start, end);
    }
}