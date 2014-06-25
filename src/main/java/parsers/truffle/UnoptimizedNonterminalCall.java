package parsers.truffle;

import com.oracle.truffle.api.frame.VirtualFrame;

public class UnoptimizedNonterminalCall extends GrammarNode {
    final NonterminalName nonterminalName;

    public UnoptimizedNonterminalCall(ParserState p, NonterminalName nonterminalName) {
        super(p);
        this.nonterminalName = nonterminalName;
    }

    @Override
    public boolean executeParse(VirtualFrame frame) {
        Alternatives alternatives = state.lookupInEnv(nonterminalName);
        return alternatives.executeParse(frame);
    }
}
