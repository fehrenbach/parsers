package org.morphling.parsers.truffle;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;

public class CachedNonterminalCallTruffle extends CachedNonterminalCall {

    private final DirectCallNode directCallNode;

    public CachedNonterminalCallTruffle(ParserState p, Alternatives alternatives, NonterminalName nonterminalName) {
        super(p, nonterminalName);
        directCallNode = Truffle.getRuntime().createDirectCallNode(Truffle.getRuntime().createCallTarget(new ParserRootNode(alternatives)));
    }

    @Override
    public boolean executeParseWithValidCache(VirtualFrame frame) {
        return ((Boolean) directCallNode.call(frame, new Object[]{})).booleanValue();
    }
}
