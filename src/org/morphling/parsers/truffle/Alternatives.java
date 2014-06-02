package org.morphling.parsers.truffle;

import com.oracle.truffle.api.frame.VirtualFrame;

public class Alternatives extends GrammarNode {
    private final @Children Sequence[] alternatives;

    public Alternatives(ParserState p, Sequence... alternatives) {
        super(p);
        this.alternatives = alternatives;
        adoptChildren();
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
