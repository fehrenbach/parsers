package org.morphling.parsers.truffle;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

public class ParserRootNode extends RootNode {
    private final @Child Alternatives alternatives;

    public ParserRootNode(Alternatives alternatives) {
        this.alternatives = alternatives;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return alternatives.executeParse(frame);
    }
}
