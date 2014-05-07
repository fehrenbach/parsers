package org.morphling.parsers.truffle;

import com.oracle.truffle.api.frame.VirtualFrame;

public class Alternatives extends GrammarNode {
    private final Sequence[] alternatives;

    public Alternatives(Sequence... alternatives) {
        this.alternatives = alternatives;
    }

    @Override
    public boolean executeParse(VirtualFrame frame) {
        for (Sequence alternative : alternatives) {
            if (alternative.executeParse(frame))
                return true;
            else
                continue;
        }
        return false;
    }
}
