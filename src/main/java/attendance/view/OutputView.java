package attendance.view;

import attendance.model.AttendanceStatus;
import attendance.model.Crew;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

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
}
