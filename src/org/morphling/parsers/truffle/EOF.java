package org.morphling.parsers.truffle;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;

public class EOF extends GrammarNode {
    public EOF(ParserState p) {
        super(p);
    }

    @Override
    public boolean executeParse(VirtualFrame frame) {
        return state.getCurrentIndex() == state.getString().length();
    }
}
