package org.morphling.parsers.truffle;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;

import java.util.HashMap;

public class UnoptimizedNonterminalCall extends GrammarPrimitive {
    final NonterminalName nonterminalName;
    final FrameSlot environmentSlot;

    public UnoptimizedNonterminalCall(NonterminalName nonterminalName, FrameSlot environmentSlot) {
        this.nonterminalName = nonterminalName;
        this.environmentSlot = environmentSlot;
    }

    @Override
    public boolean executeParse(VirtualFrame frame) {
        Alternatives alternatives = null;
        try {
            alternatives = lookupInEnv(frame, nonterminalName);
        } catch (FrameSlotTypeException e) {
            e.printStackTrace();
            return false;
        }
        return alternatives.executeParse(frame);
    }

    Alternatives lookupInEnv(VirtualFrame frame, NonterminalName nonterminalName) throws FrameSlotTypeException {
        HashMap<NonterminalName, Alternatives> env = (HashMap<NonterminalName, Alternatives>) frame.getObject(environmentSlot);
        return env.get(nonterminalName);
    }
}
