package org.morphling.parsers.truffle;

import com.oracle.truffle.api.frame.VirtualFrame;

public class CachedNonterminalCallExecute extends GrammarNode {
    final Alternatives alternatives;

    public CachedNonterminalCallExecute(ParserState p, Alternatives alternatives) {
        super(p);
        this.alternatives = alternatives;
    }

    @Override
    public boolean executeParse(VirtualFrame frame) {
        return alternatives.executeParse(frame);
    }
}
