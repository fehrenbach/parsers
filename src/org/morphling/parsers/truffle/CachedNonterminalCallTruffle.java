package org.morphling.parsers.truffle;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;

public class CachedNonterminalCallTruffle extends CachedNonterminalCall {

	private static final Object[] EMPTY_ARRAY = new Object[0];
	
	@Child
    private DirectCallNode directCallNode;

    public CachedNonterminalCallTruffle(ParserState p, CallTarget alternatives, NonterminalName nonterminalName) {
        super(p, nonterminalName);
        directCallNode = Truffle.getRuntime().createDirectCallNode(alternatives);
    }

    @Override
    public boolean executeParseWithValidCache(VirtualFrame frame) {
        return ((Boolean) directCallNode.call(frame, EMPTY_ARRAY)).booleanValue();
    }
}
