package client_side;

import commands.*;
import server_side.Server;
import utils.Utils;

import java.io.IOException;
import java.util.*;

public class Parser {
    private static Parser instance = null;
    public static Server socketToClose = null;
    public static GenericFactory<Command> commandFactory = new GenericFactory<>();
    public static HashMap<String, String> symbolTable = new HashMap<>();
    public static HashMap<String, String> bindsTable = new HashMap<>();

    private Parser() {
        commandFactory.insertProduct("openDataServer", OpenDataServerCommand.class);
        commandFactory.insertProduct("connect", ConnectCommand.class);
        commandFactory.insertProduct("while", WhileCommand.class);
        commandFactory.insertProduct("var", VarCommand.class);
        commandFactory.insertProduct("return", ReturnCommand.class);
        commandFactory.insertProduct("=", AssignCommand.class);
        commandFactory.insertProduct("disconnect", DisconnectCommand.class);
        commandFactory.insertProduct("predicate", PredicateCommand.class);
        commandFactory.insertProduct("set", SetCommand.class);

    }

    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
        }

        return instance;
    }

    public int parseScript(String[] script) {
        int result = 0;
        int loopStart = -1;
        for (int i = 0; i < script.length; i++) {
            String[] line = Lexer.getInstance().lex(script[i]);
            String command = line[0];
            try {
                if (command.equals("while")) {
                    if (executeLine(line) == 1) {
                        loopStart = i;
                    } else {
                        i += loopSize(script, i);
                    }
                } else {
                    /* If we are at the end of the loop repeat it */
                    if (command.equals("}")) {
                        i = loopStart - 1;
                    } else {
                        if (command.equals("")) {
                            line = Utils.removeFirstElement(line);
                        }
                        result = executeLine(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public int executeLine(String[] commandStr) throws IOException {
        Command command = commandFactory.createProduct(commandStr[0]);
        if (command != null) {
            commandStr = Utils.removeFirstElement(commandStr);
        } else {
            command = commandFactory.createProduct("=");
        }

        return command.doCommand(commandStr);
    }

    private int loopSize(String[] lines, int i) {
        int counter = 0;
        String command = Lexer.getInstance().lex(lines[i])[0];
        while (!command.equals("}")) {
            i++;
            counter++;
            command = Lexer.getInstance().lex(lines[i])[0];
        }
        return counter;
    }

    public static void updateBindings(String key, String newVal) {
        String bindName = Parser.bindsTable.get(key); // get SimX
        for (Map.Entry<String, String> entry : Parser.bindsTable.entrySet()) {
            if (entry.getValue().equals(bindName)) {
                Parser.symbolTable.put(entry.getKey(), newVal);
            }
        }
    }

    public static void closeSocket() {
        if (socketToClose != null) {
            socketToClose.stop();
        }
    }
}
