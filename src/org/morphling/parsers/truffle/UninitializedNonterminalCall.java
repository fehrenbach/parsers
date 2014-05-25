package org.morphling.parsers.truffle;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;

import java.util.HashMap;

public class UninitializedNonterminalCall extends GrammarPrimitive {
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
            replacementNode = new CachedNonterminalCall(alternatives);
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
