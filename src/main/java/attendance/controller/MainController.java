package attendance.controller;

import attendance.model.Crews;
import attendance.util.CrewReader;
import attendance.util.FunctionValidator;
import attendance.view.InputView;

public class MainController {

    private final InputView inputView;

    public MainController(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        Crews crews = readCrews();
        AttendanceController attendanceController = new AttendanceController(crews, inputView);
        while (true) {
            String function = getFunction();
            if (isQuit(function)) {
                break;
            }
            attendanceController.run(function);
        }

    }

    private Crews readCrews() {
        return CrewReader.readCrews();
    }

    private String getFunction() {
        String input = inputView.readFunction();
        FunctionValidator.validate(input);
        return input;
    }

    private boolean isQuit(String function) {
        return function.equals("Q");
    }
}
