package org.morphling.parsers.truffle;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;

import java.util.HashMap;

public class UninitializedNonterminalCall extends GrammarPrimitive {
    private enum CallNodeType {
        UNOPTIMIZED, CACHEDEXECUTE, CACHEDCALL
    }

    private static final CallNodeType callNodeType = CallNodeType.CACHEDEXECUTE;

    final NonterminalName nonterminalName;
    final FrameSlot environmentSlot;

    public UninitializedNonterminalCall(NonterminalName nonterminalName, FrameSlot environmentSlot) {
        this.nonterminalName = nonterminalName;
        this.environmentSlot = environmentSlot;
    }

    @Override
    public boolean executeParse(VirtualFrame frame) {
        CompilerAsserts.neverPartOfCompilation();

        Alternatives alternatives;
        GrammarPrimitive replacementNode;
        try {
            alternatives = lookupInEnv(frame, nonterminalName);

            switch (callNodeType) {
                case UNOPTIMIZED:
                    replacementNode = new UnoptimizedNonterminalCall(nonterminalName, environmentSlot);
                    break;
                case CACHEDEXECUTE:
                    replacementNode = new CachedNonterminalCallExecute(alternatives);
                    break;
                case CACHEDCALL:
                    replacementNode = new CachedNonterminalCallTruffle(alternatives);
                    break;
                default:
                    System.err.println("Not implemented org.morphling.parsers.truffle.UninitializedNonterminalCall.executeParse");
                    replacementNode = null;
            }

            replace(replacementNode);
        } catch (FrameSlotTypeException e) {
            e.printStackTrace();
            return false;
        }

        return replacementNode.executeParse(frame);
    }

    Alternatives lookupInEnv(VirtualFrame frame, NonterminalName nonterminalName) throws FrameSlotTypeException {
        HashMap<NonterminalName, Alternatives> env = (HashMap<NonterminalName, Alternatives>) frame.getObject(environmentSlot);
        return env.get(nonterminalName);
    }
}
