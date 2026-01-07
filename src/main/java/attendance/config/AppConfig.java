package attendance.config;

import attendance.controller.AttendanceController;
import attendance.view.InputView;

public class AppConfig {

    private AttendanceController attendanceController;
    private InputView inputView;

    public AttendanceController attendanceController() {
        if (attendanceController == null) {
            attendanceController = new AttendanceController(inputView());
        }
        return attendanceController;
    }

    private InputView inputView() {
        if (inputView == null) {
            inputView = new InputView();
        }
        return inputView;
    }
}
