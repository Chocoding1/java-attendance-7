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
}
