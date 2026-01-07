package attendance.controller;

import attendance.model.AttendanceStatus;
import attendance.model.CampusRunningTime;
import attendance.model.Crew;
import attendance.model.Crews;
import attendance.model.Holiday;
import attendance.service.AttendanceService;
import attendance.util.DateParser;
import attendance.util.TimeParser;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

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
            checkHoliday(DateTimes.now().toLocalDate());
            logAttendance(crews);
        }

        if (function.equals("2")) {
            updateAttendance(crews);
        }

        if (function.equals("3")) {
            findAttendanceLog(crews);
        }
    }

    private void checkHoliday(LocalDate localDate) {
        if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY
                || Holiday.isHoliday(localDate)) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일");
            String nowDate = localDate.format(formatter);
            String nowDayOfWeek = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA);
            throw new IllegalArgumentException("[ERROR] " + nowDate + " " + nowDayOfWeek + "은 등교일이 아닙니다.");
        }
    }

    private void logAttendance(Crews crews) {
        String nickname = inputView.readNickname();
        Crew crew = attendanceService.findCrew(nickname);
        LocalTime attendanceTime = getAttendanceTime();
        LocalDate nowDate = DateTimes.now().toLocalDate();
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

    private void updateAttendance(Crews crews) {
        String nickname = inputView.readAttendanceNickname();
        Crew crew = attendanceService.findCrew(nickname);
        String input = inputView.readUpdateDate();
        LocalDate updateDate = DateParser.parse(input);
        checkHoliday(updateDate);
        checkFutureDate(updateDate);
        input = inputView.readUpdateTime();
        LocalTime updateTime = TimeParser.parse(input);
        CampusRunningTime.validateRunningTime(updateTime);
        LocalDateTime updateDateTime = LocalDateTime.of(updateDate, updateTime);
        LocalTime oldAttendanceTime = crew.findAttendanceTime(updateDate);
        LocalDateTime oldLocalDateTime = LocalDateTime.of(updateDate, oldAttendanceTime);
        AttendanceStatus oldAttendanceStatus = crew.findAttendanceStatus(oldLocalDateTime);
        crew.updateAttendance(oldLocalDateTime, updateDateTime);
        outputView.printUpdateLog(crew, oldLocalDateTime, oldAttendanceStatus, updateDateTime);
    }

    private void findAttendanceLog(Crews crews) {
        String nickname = inputView.readNickname();
        Crew crew = attendanceService.findCrew(nickname);
        Map<LocalDateTime, AttendanceStatus> everydayAttendanceLog = crew.everydayAttendanceLog(
                DateTimes.now());
        outputView.printEveryAttendanceLog(crew, everydayAttendanceLog);
    }

    private void checkFutureDate(LocalDate localDate) {
        if (localDate.isAfter(DateTimes.now().toLocalDate())) {
            throw new IllegalArgumentException("[ERROR] 아직 수정할 수 없습니다.");
        }
    }
}
