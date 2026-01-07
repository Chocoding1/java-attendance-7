package attendance.model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Crew {

    private final String nickname;
    private Map<LocalDateTime, AttendanceStatus> attendanceLog;

    public Crew(String nickname) {
        this.nickname = nickname;
        this.attendanceLog = new HashMap<>();
    }

    public void logAttendance(LocalDateTime localDateTime) {
        validateDuplicate(localDateTime);
        AttendanceStatus attendanceStatus = getAttendanceStatus(localDateTime);
        attendanceLog.put(localDateTime, attendanceStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(nickname, crew.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }

    public boolean isSameNickname(String nickname) {
        return this.nickname.equals(nickname);
    }

    public AttendanceStatus AttendanceStatusForDate(LocalDateTime localDateTime) {
        return attendanceLog.get(localDateTime);
    }

    private void validateDuplicate(LocalDateTime localDateTime) {
        for (LocalDateTime dateTime : attendanceLog.keySet()) {
            if (dateTime.toLocalDate().equals(localDateTime.toLocalDate())) {
                throw new IllegalArgumentException("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
            }
        }
    }

    private AttendanceStatus getAttendanceStatus(LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        LocalTime startTime = StartTimePerDayOfWeek.getStartTime(dayOfWeek);
        LocalTime attendanceTime = localDateTime.toLocalTime();
        return getAttendanceStatus(attendanceTime, startTime);
    }

    private static AttendanceStatus getAttendanceStatus(LocalTime attendanceTime, LocalTime startTime) {
        if (attendanceTime.isBefore(startTime)) {
            return AttendanceStatus.ATTENDANCE;
        }
        Duration between = Duration.between(startTime, attendanceTime);
        long minutes = between.toMinutes();
        if (minutes > 30) {
            return AttendanceStatus.ABSENCE;
        }
        if (minutes > 5) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }
}
