package org.morphling.parsers.org.morphling.parsers.interpreter;

public class Character extends Node {
    private final char c;

    public Character(char c) {
        this.c = c;
    }

    @Override
    boolean execute(ParserState ps) {
        if (ps.charAt(ps.pos()) == c) {
            ps.incPos();
            return true;
        }
        return false;
    }
}
