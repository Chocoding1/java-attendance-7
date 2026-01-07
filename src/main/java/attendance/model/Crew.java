package attendance.model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Crew {

    private final String nickname;
    private Map<LocalDateTime, AttendanceStatus> attendanceLog;
    private int attendanceCount = 0;
    private int lateCount = 0;
    private int absenceCount = 0;

    public Crew(String nickname) {
        this.nickname = nickname;
        this.attendanceLog = new HashMap<>();
    }

    public String getNickname() {
        return nickname;
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public DismissalStatus dismissalStatus() {
        return DismissalStatus.from(absenceCount + lateCount / 3);
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

    public LocalTime findAttendanceTime(LocalDate updateDate) {
        for (LocalDateTime loggedDateTime : attendanceLog.keySet()) {
            if (loggedDateTime.toLocalDate().equals(updateDate)) {
                return loggedDateTime.toLocalTime();
            }
        }
        throw new IllegalArgumentException("[ERROR] 기록되지 않은 날짜입니다.");
    }

    public AttendanceStatus findAttendanceStatus(LocalDateTime localDateTime) {
        return attendanceLog.get(localDateTime);
    }

    public void updateAttendance(LocalDateTime oldDateTime, LocalDateTime updateDateTime) {
        attendanceLog.remove(oldDateTime);
        logAttendance(updateDateTime);
    }

    public Map<LocalDateTime, AttendanceStatus> everydayAttendanceLog(LocalDateTime now) {
        LocalDate nowDate = now.toLocalDate();
        LocalDate firstDate = now.withDayOfMonth(1).toLocalDate();
        Map<LocalDateTime, AttendanceStatus> everydayAttendanceLog = new HashMap<>();
        while (firstDate.isBefore(nowDate)) {
            if (isHoliday(firstDate)) {
                firstDate = firstDate.plusDays(1);
                continue;
            }
            if (isLoggedDate(firstDate)) {
                LocalDateTime attendanceDateTime = getAttendanceDateTime(firstDate);
                everydayAttendanceLog.put(attendanceDateTime, attendanceLog.get(attendanceDateTime));
                firstDate = firstDate.plusDays(1);
                addAttendanceStatusCount(attendanceLog.get(attendanceDateTime));
                continue;
            }
            everydayAttendanceLog.put(LocalDateTime.of(firstDate, LocalTime.of(0, 0)), AttendanceStatus.ABSENCE);
            firstDate = firstDate.plusDays(1);
            absenceCount++;
        }
        return everydayAttendanceLog;
    }

    private boolean isHoliday(LocalDate localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY
                || Holiday.isHoliday(localDate);
    }

    private void addAttendanceStatusCount(AttendanceStatus attendanceStatus) {
        if (attendanceStatus.equals(AttendanceStatus.ATTENDANCE)) {
            attendanceCount++;
        }
        if (attendanceStatus.equals(AttendanceStatus.LATE)) {
            lateCount++;
        }
        if (attendanceStatus.equals(AttendanceStatus.ABSENCE) || attendanceStatus.equals(AttendanceStatus.LATE_ABSENCE)) {
            absenceCount++;
        }
    }

    private boolean isLoggedDate(LocalDate localdate) {
        for (LocalDateTime localDateTime : attendanceLog.keySet()) {
            if (localDateTime.toLocalDate().equals(localdate)) {
                return true;
            }
        }
        return false;
    }

    private LocalDateTime getAttendanceDateTime(LocalDate firstDate) {
        for (LocalDateTime localDateTime : attendanceLog.keySet()) {
            if (localDateTime.toLocalDate().equals(firstDate)) {
                return localDateTime;
            }
        }
        throw new IllegalArgumentException("[ERROR] 기록되지 않은 날짜입니다.");
    }

    private void validateDuplicate(LocalDateTime localDateTime) {
        for (LocalDateTime loggedDateTime : attendanceLog.keySet()) {
            if (loggedDateTime.toLocalDate().equals(localDateTime.toLocalDate())) {
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
            return AttendanceStatus.LATE_ABSENCE;
        }
        if (minutes > 5) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }
}
