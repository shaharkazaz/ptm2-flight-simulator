package commands;

import client_side.Parser;
import utils.Utils;

public class PredicateCommand implements Command {
    @Override
    public int doCommand(String[] args) {
        boolean result = false;

        String leftS = args[0];
        double left;
        String temp = Utils.isNumber(leftS) ? leftS : Parser.symbolTable.get(leftS);
        left = Double.parseDouble(temp);

        String rightS = args[2];
        double right;
        temp = Utils.isNumber(rightS) ? rightS : Parser.symbolTable.get(rightS);
        right = Double.parseDouble(temp);

        String operand = args[1];
        switch (operand) {
            case ">":
                result = left > right;
                break;
            case "<":
                result = left < right;
                break;
            case "==":
                result = left == right;
                break;
            case "!=":
                result = left != right;
                break;
            case ">=":
                result = left >= right;
                break;
            case "<=":
                result = left <= right;
                break;
        }

        return result ? 1 : 0;
    }
}
