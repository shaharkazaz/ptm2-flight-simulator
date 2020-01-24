package test;

import client_side.Parser;

public class MyInterpreter {

    public static int interpret(String[] lines) {
        // call your interpreter here - call Parser / Lexer
        return Parser.getInstance().parseScript(lines);
    }
}
