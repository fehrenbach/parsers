package org.morphling.parsers.interpreter;

public class Eof extends Node {
    @Override
    boolean execute(ParserState ps) {
        return ps.atEnd();
    }
}
