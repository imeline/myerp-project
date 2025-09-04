package erp.global.util.time;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Periods {
    public static DateRange resolve(DatePeriod period, LocalDate today) {
        if (period == null || period == DatePeriod.ALL)
            return DateRange.of(null, null);
        return switch (period) {
            case LAST_7_DAYS -> DateRange.of(today.minusDays(6), today);
            case LAST_30_DAYS -> DateRange.of(today.minusDays(29), today);
            case LAST_90_DAYS -> DateRange.of(today.minusDays(89), today);
            case THIS_MONTH -> {
                YearMonth yearMonth = YearMonth.from(today);
                yield DateRange.of(yearMonth.atDay(1), yearMonth.atEndOfMonth());
            }
            case LAST_MONTH -> {
                YearMonth yearMonth = YearMonth.from(today).minusMonths(1);
                yield DateRange.of(yearMonth.atDay(1), yearMonth.atEndOfMonth());
            }
            case THIS_YEAR ->
                    DateRange.of(LocalDate.of(today.getYear(), 1, 1), LocalDate.of(today.getYear(), 12, 31));
            default -> DateRange.of(null, null);
        };
    }
}
