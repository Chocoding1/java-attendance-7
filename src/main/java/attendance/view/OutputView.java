package attendance.view;

import attendance.model.AttendanceStatus;
import attendance.model.Crew;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

public class OutputView {

    public static final String PRINT_EVERY_ATTENDANCE_LOG_TITLE = "이번 달 %s의 출석 기록입니다.";

    public void printAttendanceLog(Crew crew, LocalDateTime localDateTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String date = localDateTime.toLocalDate().format(dateFormatter);
        String time = localDateTime.toLocalTime().format(timeFormatter);
        String dayOfWeek = localDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        AttendanceStatus attendanceStatus = crew.AttendanceStatusForDate(localDateTime);
        System.out.println(date + " " + dayOfWeek + " " + time + " (" + attendanceStatus.getName() + ")");
    }

    public void printUpdateLog(Crew crew, LocalDateTime oldLocalDateTime, AttendanceStatus oldAttendanceStatus, LocalDateTime updateDateTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String oldDate = oldLocalDateTime.toLocalDate().format(dateFormatter);
        String oldTime = oldLocalDateTime.toLocalTime().format(timeFormatter);
        String oldDayOfWeek = oldLocalDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        String updateTime = updateDateTime.toLocalTime().format(timeFormatter);
        AttendanceStatus updateAttendanceStatus = crew.AttendanceStatusForDate(updateDateTime);

        System.out.println(oldDate + " " + oldDayOfWeek + " " + oldTime + " (" + oldAttendanceStatus.getName() + ") -> "
                + updateTime + " (" + updateAttendanceStatus.getName() + ") 수정 완료!");
    }

    public void printEveryAttendanceLog(Crew crew, Map<LocalDateTime, AttendanceStatus> everydayAttendanceLog) {
        System.out.printf(PRINT_EVERY_ATTENDANCE_LOG_TITLE, crew.getNickname());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일");
        for (LocalDateTime localDateTime : everydayAttendanceLog.keySet()) {
            String date = localDateTime.toLocalDate().format(dateFormatter);
            String time = getTime(localDateTime, everydayAttendanceLog);
            String dayOfWeek = localDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
            AttendanceStatus attendanceStatus = everydayAttendanceLog.get(localDateTime);
            System.out.println(date + " " + dayOfWeek + " " + time + " (" + attendanceStatus.getName() + ")");
        }
        System.out.println(System.lineSeparator());
        System.out.println("출석: " + crew.getAttendanceCount() + "회");
        System.out.println("지각: " + crew.getLateCount() + "회");
        System.out.println("결석: " + crew.getAbsenceCount() + "회");
        System.out.println(System.lineSeparator());
        System.out.println(crew.dismissalStatus().getName() + " 대상자입니다.");
    }

    private String getTime(LocalDateTime localDateTime, Map<LocalDateTime, AttendanceStatus> everydayAttendanceLog) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        AttendanceStatus attendanceStatus = everydayAttendanceLog.get(localDateTime);
        if (attendanceStatus.equals(AttendanceStatus.ABSENCE)) {
            return "--:--";
        }
        return localDateTime.toLocalTime().format(timeFormatter);
    }
}
