package attendance.model;

public enum DismissalStatus {
    NOTHING("장학생", 0),
    WARNING("경고 대상자", 2),
    INTERVIEW("면담 대상자", 3),
    DISMISSAL("제적 대상자", 5),
    ;

    private final String name;
    private final int baseCount;

    DismissalStatus(String name, int baseCount) {
        this.name = name;
        this.baseCount = baseCount;
    }

    public String getName() {
        return name;
    }

    public static DismissalStatus from(int count) {
        if (DISMISSAL.baseCount < count) {
            return DISMISSAL;
        }

        if (INTERVIEW.baseCount <= count) {
            return INTERVIEW;
        }

        if (WARNING.baseCount <= count) {
            return WARNING;
        }

        return NOTHING;
    }
}
