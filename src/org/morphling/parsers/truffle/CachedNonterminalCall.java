package org.morphling.parsers.truffle;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.InvalidAssumptionException;

public abstract class CachedNonterminalCall extends GrammarNode {
	final NonterminalName nonterminalName;
    final Assumption grammarUnchanged;

    protected CachedNonterminalCall(ParserState p, NonterminalName nonterminalName) {
        super(p);
        this.nonterminalName = nonterminalName;
        this.grammarUnchanged = p.cacheNonterminal(nonterminalName);
    }

    @Override
    public boolean executeParse(VirtualFrame frame) {
        try {
            grammarUnchanged.check();
            return executeParseWithValidCache(frame);
        } catch (InvalidAssumptionException e) {
            GrammarNode replacementNode = new UninitializedNonterminalCall(state, nonterminalName);
            replace(replacementNode);
            return replacementNode.executeParse(frame);
        }
    }

    abstract boolean executeParseWithValidCache(VirtualFrame frame);
}
