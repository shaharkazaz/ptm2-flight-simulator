package commands;

import client_side.Parser;
import client_side.SimulatorSocket;
import server_side.FlightClientHandler;

public class DisconnectCommand implements Command {
    @Override
    public int doCommand(String[] array) {
        try {
            SimulatorSocket.getInstance().stop();
            FlightClientHandler.stop = true;
            Parser.closeSocket();
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
