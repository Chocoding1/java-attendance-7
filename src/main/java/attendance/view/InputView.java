package attendance.view;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class InputView {

    private static final String READ_FUNCTION_FORMAT = """
            오늘은 %s %s입니다. 기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료
            %n
            """;
    public static final String READ_NICKNAME_NOTICE = "닉네임을 입력해 주세요.";
    public static final String READ_ATTENDANCE_TIME_NOTICE = "등교 시간을 입력해 주세요.";
    public static final String READ_UPDATE_ATTENDANCE_NICKNAME_NOTICE = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    public static final String READ_UPDATE_DATE_NOTICE = "수정하려는 날짜(일)를 입력해 주세요.";
    public static final String READ_UPDATE_TIME_NOTICE = "언제로 변경하겠습니까?";

    public String readFunction() {
        List<String> now = getNowDateAndDayOfWeek();
        System.out.printf(READ_FUNCTION_FORMAT, now.get(0), now.get(1));
        return Console.readLine();
    }

    private List<String> getNowDateAndDayOfWeek() {
        LocalDateTime now = DateTimes.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일");

        String nowDate = now.format(formatter);
        String nowDayOfWeek = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA);
        return List.of(nowDate, nowDayOfWeek);
    }

    public String readNickname() {
        System.out.println(READ_NICKNAME_NOTICE);
        return Console.readLine();
    }

    public String readAttendanceTime() {
        System.out.println(READ_ATTENDANCE_TIME_NOTICE);
        return Console.readLine();
    }

    public String readAttendanceNickname() {
        System.out.println(READ_UPDATE_ATTENDANCE_NICKNAME_NOTICE);
        return Console.readLine();
    }

    public String readUpdateDate() {
        System.out.println(READ_UPDATE_DATE_NOTICE);
        return Console.readLine();
    }

    public String readUpdateTime() {
        System.out.println(READ_UPDATE_TIME_NOTICE);
        return Console.readLine();
    }
}
