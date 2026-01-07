package attendance.model;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATE("지각"),
    LATE_ABSENCE("결석"),
    ABSENCE("결석"),
    ;

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
