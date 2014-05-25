package org.morphling.parsers.truffle;

import com.oracle.truffle.api.frame.VirtualFrame;

public class Sequence extends GrammarNode {
    private final @Children GrammarPrimitive[] sequence;

    public Sequence(GrammarPrimitive... sequence) {
        this.sequence = sequence;
        adoptChildren();
    }

    @Override
    public boolean executeParse(VirtualFrame frame) {
        for (GrammarPrimitive grammarPrimitive : sequence) {
            if (grammarPrimitive.executeParse(frame))
                continue;
            else
                return false;
        }
        return true;
    }
}
