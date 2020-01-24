package model;

import commands.Command;
import commands.OpenDataServerCommand;
import test.MyInterpreter;

import java.io.IOException;

public class SimulatorModel implements Model {

    static final String flightControl = "/controls/flight/";
    static final String engineControl = "/controls/engines/current-engine/";

    private static void setFlightControl(String prop, double val, String target) {
        String value = prop + " " + val;
        System.out.println(value);
        
        String[] command = new String[]{"set " + target + value};
        MyInterpreter.interpret(command);
    }

    @Override
    public void setThrottle(double val) {
        setFlightControl("throttle", val, engineControl);
    }

    @Override
    public void setRudder(double val) {
        setFlightControl("rudder", val, flightControl);
    }

    @Override
    public void setAileron(double val) {
        setFlightControl("aileron", val, flightControl);
    }

    @Override
    public void setElevator(double val) {
        setFlightControl("elevator", val, flightControl);
    }

    public void openServer(String port, String freq) throws IOException {
        System.out.println("openDataServer Command pressed");
        MyInterpreter.interpret(new String[]{"openDataServer " + port + " " + freq});
    }

    public void connectToSimulator(String ip, int port) {
        MyInterpreter.interpret(new String[]{"connect " + ip + " " + port});
    }
}
