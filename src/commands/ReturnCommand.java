package commands;

import client_side.Parser;
import shunting_yard.Algo;

import java.io.IOException;

public class ReturnCommand implements Command {

    @Override
    public int doCommand(String[] args) {
        StringBuilder exp = new StringBuilder();
        for (String arg : args) {
            exp.append(arg);
        }

        return (int)Algo.calc(exp.toString());
    }
}
