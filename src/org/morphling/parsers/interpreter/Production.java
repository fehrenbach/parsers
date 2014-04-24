package org.morphling.parsers.interpreter;

public class Production extends Node {
    private final String name;

    public Production(String name) {
        this.name = name;
    }

    @Override
    boolean execute(ParserState ps) {
        return ps.lookup(name).execute(ps);
    }
}
