package org.morphling.parsers.truffle;

import com.oracle.truffle.api.frame.VirtualFrame;

public class CachedNonterminalCall extends GrammarPrimitive {
    final Alternatives alternatives;

    public CachedNonterminalCall(Alternatives alternatives) {
        super();
        this.alternatives = alternatives;
    }

    @Override
    public boolean executeParse(VirtualFrame frame) {
        return alternatives.executeParse(frame);
    }
}
