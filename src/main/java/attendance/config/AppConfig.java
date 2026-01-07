package attendance.config;

import attendance.controller.MainController;
import attendance.view.InputView;

public class AppConfig {

    private MainController mainController;
    private InputView inputView;

    public MainController mainController() {
        if (mainController == null) {
            mainController = new MainController(inputView());
        }
        return mainController;
    }

    private InputView inputView() {
        if (inputView == null) {
            inputView = new InputView();
        }
        return inputView;
    }
}
