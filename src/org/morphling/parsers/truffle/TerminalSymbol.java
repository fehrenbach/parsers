package org.morphling.parsers.truffle;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;

public class TerminalSymbol extends GrammarPrimitive {
    private final int c;
    private final FrameSlot currentIndexSlot;
    private final FrameSlot stringSlot;

    public TerminalSymbol(int c, FrameSlot currentIndexSlot, FrameSlot stringSlot) {
        this.c = c;
        this.currentIndexSlot = currentIndexSlot;
        this.stringSlot = stringSlot;
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

    int getCurrentIndex(VirtualFrame frame) throws FrameSlotTypeException {
        return frame.getInt(currentIndexSlot);
    }

    String getString(VirtualFrame frame) throws FrameSlotTypeException {
        return (String) frame.getObject(stringSlot);
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
