package attendance.model;

import java.time.LocalDate;

public enum Holiday {
    CHRISTMAS(LocalDate.of(2024, 12, 25)),
    ;

    private final LocalDate localDate;

    Holiday(LocalDate localDate) {
        this.localDate = localDate;
    }

    public static boolean isHoliday(LocalDate now) {
        for (Holiday holiday : values()) {
            if (holiday.localDate == now) {
                return true;
            }
        }
        return false;
    }
}
