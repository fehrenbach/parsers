package org.morphling.parsers.truffle;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;

public class EOF extends GrammarPrimitive {
    final FrameSlot currentIndexSlot;
    final FrameSlot stringSlot;

    public EOF(FrameSlot currentIndexSlot, FrameSlot stringSlot) {
        this.currentIndexSlot = currentIndexSlot;
        this.stringSlot = stringSlot;
    }

    @Override
    public boolean executeParse(VirtualFrame frame) {
        try {
            return getCurrentIndex(frame) == getString(frame).length();
        } catch (FrameSlotTypeException e) {
            e.printStackTrace();
        }
        return false;
    }

    int getCurrentIndex(VirtualFrame frame) throws FrameSlotTypeException {
        return frame.getInt(currentIndexSlot);
    }

    String getString(VirtualFrame frame) throws FrameSlotTypeException {
        return (String) frame.getObject(stringSlot);
    }
}
