package attendance.util;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;

public class DateParser {

    public static LocalDate parse(String input) {
        int date;
        try {
            date = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }
        return LocalDate.of(DateTimes.now().getYear(), DateTimes.now().getMonth(), date);
    }
}
