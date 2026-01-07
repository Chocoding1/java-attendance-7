package attendance.util;

import attendance.model.Crew;
import attendance.model.Crews;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrewReader {

    private static final String FILE_PATH = "src/main/resources/";
    private static final String FILE_NAME = "attendances.csv";

    private static final String COMMA = ",";

    public static Crews readCrews() {
        List<String> lines =  readLines();
        List<Crew> crews = getCrews(lines);
        return new Crews(crews);
    }

    private static List<String> readLines() {
        File file = new File(FILE_PATH + FILE_NAME);
        List<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 파일입니다.");
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 파일을 읽는 도중 예외가 발생했습니다.");
        }
        return lines;
    }

    private static List<Crew> getCrews(List<String> lines) {
        Set<String> nicknames = new HashSet<>();
        List<Crew> crews = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String[] tokens = lines.get(i).split(COMMA);
            Crew crew = getCrew(nicknames, crews, tokens);
            LocalDateTime localDateTime = getLocalDateTime(tokens);
            crew.logAttendance(localDateTime);
        }
        return crews;
    }

    private static Crew getCrew(Set<String> nicknames, List<Crew> crews, String[] tokens) {
        String nickname = tokens[0];
        if (isAlreadyCreated(nicknames, nickname)) {
            return findByNickname(crews, nickname);
        }
        return createCrewAndLog(nicknames, crews, nickname);
    }

    private static boolean isAlreadyCreated(Set<String> nicknames, String nickname) {
        return nicknames.contains(nickname);
    }

    private static Crew findByNickname(List<Crew> crews, String nickname) {
        for (Crew crew : crews) {
            if (crew.isSameNickname(nickname)) {
                return crew;
            }
        }
        throw new IllegalArgumentException("[ERROR] 존재하지 않는 크루입니다.");
    }

    private static Crew createCrewAndLog(Set<String> nicknames, List<Crew> crews, String nickname) {
        nicknames.add(nickname);
        Crew crew = new Crew(nickname);
        crews.add(crew);
        return crew;
    }

    private static LocalDateTime getLocalDateTime(String[] tokens) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(tokens[1], formatter);
    }
}
