package commands;

import client_side.Parser;
import server_side.ClientHandler;
import server_side.FlightClientHandler;
import server_side.MySerialServer;
import server_side.Server;

public class OpenDataServerCommand implements Command {

    @Override
    public int doCommand(String[] args) {
        int port = Integer.parseInt(args[0]);
        int timeout = Integer.parseInt(args[1]);
        ClientHandler flightClient = new FlightClientHandler(timeout);
        Server server = new MySerialServer();
        server.start(port, flightClient);
        Parser.socketToClose = server;
        return 0;
    }
}
