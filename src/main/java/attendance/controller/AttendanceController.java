package attendance.controller;

import attendance.model.CampusRunningTime;
import attendance.model.Crew;
import attendance.model.Crews;
import attendance.model.Holiday;
import attendance.service.AttendanceService;
import attendance.util.TimeParser;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendanceController {

    private final Crews crews;
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceService attendanceService;

    public AttendanceController(Crews crews, InputView inputView) {
        this.crews = crews;
        this.inputView = inputView;
        this.outputView = new OutputView();
        this.attendanceService = new AttendanceService(crews);
    }

    public void run(String function) {
        if (function.equals("1")) {
            checkHoliday();
            logAttendance(crews);
        }
    }

    private void checkHoliday() {
        LocalDate now = LocalDate.now();
        if (now.getDayOfWeek() == DayOfWeek.SATURDAY || now.getDayOfWeek() == DayOfWeek.SUNDAY
                || Holiday.isHoliday(now)) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일");
            String nowDate = now.format(formatter);
            String nowDayOfWeek = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA);
            throw new IllegalArgumentException("[ERROR] " + nowDate + " " + nowDayOfWeek + "은 등교일이 아닙니다.");
        }
    }

    private void logAttendance(Crews crews) {
        String nickname = inputView.readNickname();
        Crew crew = attendanceService.findCrew(nickname);
        LocalTime attendanceTime = getAttendanceTime();
        LocalDate nowDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(nowDate, attendanceTime);
        crew.logAttendance(localDateTime);
        outputView.printAttendanceLog(crew, localDateTime);
    }

    private LocalTime getAttendanceTime() {
        String input = inputView.readAttendanceTime();
        LocalTime localTime = TimeParser.parse(input);
        CampusRunningTime.validateRunningTime(localTime);
        return localTime;
    }
}
