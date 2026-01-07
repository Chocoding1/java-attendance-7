package attendance.view;

import camp.nextstep.edu.missionutils.Console;
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

    public String readFunction() {
        List<String> now = getNowDateAndDayOfWeek();
        System.out.printf(READ_FUNCTION_FORMAT, now.get(0), now.get(1));
        return Console.readLine();
    }

    private List<String> getNowDateAndDayOfWeek() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일");

        String nowDate = now.format(formatter);
        String nowDayOfWeek = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA);
        return List.of(nowDate, nowDayOfWeek);
    }
}
