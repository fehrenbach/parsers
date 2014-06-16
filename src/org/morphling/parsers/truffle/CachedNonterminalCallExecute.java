package org.morphling.parsers.truffle;

import com.oracle.truffle.api.frame.VirtualFrame;

public class CachedNonterminalCallExecute extends CachedNonterminalCall {
    final Alternatives alternatives;
    final NonterminalName nonterminalName;

    public CachedNonterminalCallExecute(ParserState p, Alternatives alternatives, NonterminalName nonterminalName) {
        super(p, nonterminalName);
        this.alternatives = alternatives;
        this.nonterminalName = nonterminalName;
    }

    @Override
    public boolean executeParseWithValidCache(VirtualFrame frame) {
        return alternatives.executeParse(frame);
    }
}

