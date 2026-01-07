package attendance.service;

import attendance.model.Crew;
import attendance.model.Crews;

public class AttendanceService {

    private final Crews crews;

    public AttendanceService(Crews crews) {
        this.crews = crews;
    }

    public Crew findCrew(String nickname) {
        return crews.findByNickname(nickname);
    }
}
