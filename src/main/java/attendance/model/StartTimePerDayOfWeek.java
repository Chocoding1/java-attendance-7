package attendance.model;

import static java.time.DayOfWeek.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

public enum StartTimePerDayOfWeek {
    MON(MONDAY, LocalTime.of(13, 0)),
    TUE(TUESDAY, LocalTime.of(10, 0)),
    WED(WEDNESDAY, LocalTime.of(10, 0)),
    THU(THURSDAY, LocalTime.of(10, 0)),
    FRI(FRIDAY, LocalTime.of(10, 0)),
    SAT(SATURDAY, LocalTime.of(10, 0)),
    SUN(SUNDAY, LocalTime.of(10, 0)),
    ;

    private final java.time.DayOfWeek dayOfWeek;
    private final LocalTime startTime;

    StartTimePerDayOfWeek(DayOfWeek dayOfWeek, LocalTime startTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    public static LocalTime getStartTime(DayOfWeek dayOfWeek) {
        for (StartTimePerDayOfWeek startTimePerDayOfWeek : values()) {
            if (startTimePerDayOfWeek.dayOfWeek.equals(dayOfWeek)) {
                return startTimePerDayOfWeek.startTime;
            }
        }
        throw new IllegalArgumentException("[ERROR] 존재하지 않는 요일입니다.");
    }
}
