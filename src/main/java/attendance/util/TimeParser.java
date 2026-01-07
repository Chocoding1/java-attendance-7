package attendance.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.IllformedLocaleException;

public class TimeParser {

    public static LocalTime parse(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            return LocalTime.parse(input, formatter);
        } catch (DateTimeParseException e) {
            throw new IllformedLocaleException("[ERROR] 잘못된 형식을 입력하셨습니다.");
        }
    }
}
