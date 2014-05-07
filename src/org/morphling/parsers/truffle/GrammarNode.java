package org.morphling.parsers.truffle;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;

public abstract class GrammarNode extends Node {
    public abstract boolean executeParse(VirtualFrame frame);
}
