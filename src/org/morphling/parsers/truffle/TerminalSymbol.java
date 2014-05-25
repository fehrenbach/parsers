package org.morphling.parsers.truffle;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;

public class TerminalSymbol extends EOF {
    private final int c;

    public TerminalSymbol(int c, FrameSlot currentIndexSlot, FrameSlot stringSlot) {
        super(currentIndexSlot, stringSlot);
        this.c = c;
    }

    @Override
    public boolean executeParse(VirtualFrame frame) {
        try {
            int currentCodePoint = getCurrentChar(frame);
            if (currentCodePoint == c) {
                incCurrentChar(frame);
                return true;
            } else {
                return false;
            }
        } catch (FrameSlotTypeException e) {
            e.printStackTrace();
            return false;
        }
    }

    int getCurrentChar(VirtualFrame frame) throws FrameSlotTypeException {
        int currentIndex = getCurrentIndex(frame);
        int codePoint = getString(frame).codePointAt(currentIndex);
        return codePoint;
    }

    void incCurrentChar(VirtualFrame frame) throws FrameSlotTypeException {
        frame.setInt(currentIndexSlot, getCurrentIndex(frame) + 1);
    }
}
