package org.morphling.parsers.truffle;

import com.oracle.truffle.api.frame.VirtualFrame;

public class TerminalSymbol extends EOF {
    private final int c;

    public TerminalSymbol(ParserState p, int c) {
        super(p);
        this.c = c;
    }

    @Override
    public boolean executeParse(VirtualFrame frame) {
        int currentCodePoint = state.getCurrentChar();
        if (currentCodePoint == c) {
            state.incCurrentChar();
            return true;
        } else {
            return false;
        }
    }
}
