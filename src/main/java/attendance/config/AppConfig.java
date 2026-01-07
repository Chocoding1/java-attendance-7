package attendance.config;

import attendance.controller.AttendanceController;

public class AppConfig {

    private AttendanceController attendanceController;

    public AttendanceController attendanceController() {
        if (attendanceController == null) {
            attendanceController = new AttendanceController();
        }
        return attendanceController;
    }
}
