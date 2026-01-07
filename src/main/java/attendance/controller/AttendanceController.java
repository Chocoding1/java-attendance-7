package attendance.controller;

import attendance.model.Crews;
import attendance.util.CrewReader;
import attendance.util.FunctionValidator;
import attendance.view.InputView;

public class AttendanceController {

    private final InputView inputView;

    public AttendanceController(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        Crews crews = readCrews();
        String fucntion = getFunction();
    }

    private String getFunction() {
        String input = inputView.readFunction();
        FunctionValidator.validate(input);
        return input;
    }

    private Crews readCrews() {
        return CrewReader.readCrews();
    }
}
