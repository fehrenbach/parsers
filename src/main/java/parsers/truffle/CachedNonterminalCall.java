package parsers.truffle;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.InvalidAssumptionException;

public class CachedNonterminalCall extends GrammarNode {

	private static final Object[] EMPTY_ARRAY = new Object[0];
    final NonterminalName nonterminalName;
    final Assumption grammarUnchanged;

    @Child
    private DirectCallNode directCallNode;

    public CachedNonterminalCall(ParserState p, CallTarget alternatives, NonterminalName nonterminalName) {
        super(p);
        this.nonterminalName = nonterminalName;
        this.grammarUnchanged = p.cacheNonterminal(nonterminalName);
        directCallNode = Truffle.getRuntime().createDirectCallNode(alternatives);
    }

    @Override
    public boolean executeParse(VirtualFrame frame) {
        try {
            grammarUnchanged.check();
            return (Boolean) directCallNode.call(frame, EMPTY_ARRAY);
        } catch (InvalidAssumptionException e) {
            GrammarNode replacementNode = new UninitializedNonterminalCall(state, nonterminalName);
            replace(replacementNode, "Nonterminal " + nonterminalName + " changed");
            return replacementNode.executeParse(frame);
        }
    }
}
