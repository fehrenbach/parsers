package parsers.truffle;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;

public class UninitializedNonterminalCall extends GrammarNode {
    public enum CallNodeType {
        UNOPTIMIZED, CACHED
    }

    @CompilerDirectives.CompilationFinal
    public static CallNodeType callNodeType = CallNodeType.CACHED;

    final NonterminalName nonterminalName;

    public UninitializedNonterminalCall(ParserState p, NonterminalName nonterminalName) {
        super(p);
        this.nonterminalName = nonterminalName;
    }

    @Override
    public boolean executeParse(VirtualFrame frame) {
        CompilerAsserts.neverPartOfCompilation();

        CallTarget alternatives;
        GrammarNode replacementNode;


        alternatives = state.lookupInEnv(nonterminalName);
        
        switch (callNodeType) {
            case UNOPTIMIZED:
                replacementNode = new UnoptimizedNonterminalCall(state, nonterminalName);
                break;
            case CACHED:
                replacementNode = new CachedNonterminalCall(state, alternatives, nonterminalName);
                break;
            default:
                System.err.println("Not implemented parsers.truffle.UninitializedNonterminalCall.executeParse");
                replacementNode = null;
        }


        replace(replacementNode, "Call nonterminal " + nonterminalName);


        return replacementNode.executeParse(frame);
    }
}
