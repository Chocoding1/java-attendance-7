package attendance.model;

import java.time.LocalTime;
import java.util.IllformedLocaleException;

public enum CampusRunningTime {
    START_TIME(LocalTime.of(8, 0)),
    END_TIME(LocalTime.of(23, 0))
    ;

    private final LocalTime localTime;

    CampusRunningTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public static void validateRunningTime(LocalTime localTime) {
        if (localTime.isBefore(START_TIME.localTime) || localTime.isAfter(END_TIME.localTime)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간에만 출석이 가능합니다.");
        }
    }
}
