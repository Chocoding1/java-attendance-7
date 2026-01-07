package attendance.controller;

import attendance.model.Crews;
import attendance.util.CrewReader;

public class AttendanceController {


    public void run() {
        Crews crews = readCrews();
        System.out.println("crews = " + crews);
    }

    private Crews readCrews() {
        return CrewReader.readCrews();
    }
}
