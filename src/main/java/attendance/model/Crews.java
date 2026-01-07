package attendance.model;

import java.util.List;

public class Crews {

    private final List<Crew> crews;

    public Crews(List<Crew> crews) {
        this.crews = crews;
    }

    public Crew findByNickname(String nickname) {
        for (Crew crew : crews) {
            if (crew.isSameNickname(nickname)) {
                return crew;
            }
        }
        throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
    }
}
