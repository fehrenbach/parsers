package parsers.truffle;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;

public abstract class GrammarNode extends Node {
    final ParserState state;

    GrammarNode(ParserState state) {
        this.state = state;
    }

    public abstract boolean executeParse(VirtualFrame frame);
}
