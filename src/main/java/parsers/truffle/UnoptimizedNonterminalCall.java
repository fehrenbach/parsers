package parsers.truffle;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.IndirectCallNode;

public class UnoptimizedNonterminalCall extends GrammarNode {
    final NonterminalName nonterminalName;
    private static final Object[] EMPTY_ARRAY = new Object[0];

    @Child IndirectCallNode indirectCall = Truffle.getRuntime().createIndirectCallNode();

    public UnoptimizedNonterminalCall(ParserState p, NonterminalName nonterminalName) {
        super(p);
        this.nonterminalName = nonterminalName;
    }

    @Override
    public boolean executeParse(VirtualFrame frame) {
    	return (Boolean)indirectCall.call(frame, state.lookupInEnv(nonterminalName), EMPTY_ARRAY);
    }

    @Override
    public String toString() {
        return "UnoptimizedNonterminalCall{" +
                "nonterminalName=" + nonterminalName +
                '}';
    }
}
