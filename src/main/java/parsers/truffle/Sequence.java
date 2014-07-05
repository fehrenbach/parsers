package parsers.truffle;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;

import java.util.Arrays;

public class Sequence extends GrammarNode {
    private final @Children GrammarNode[] sequence;

    public Sequence(ParserState p, GrammarNode... sequence) {
        super(p);
        this.sequence = sequence;
    }

    @Override @ExplodeLoop
    public boolean executeParse(VirtualFrame frame) {
        for (GrammarNode grammarNode : sequence) {
            if (grammarNode.executeParse(frame))
                continue;
            else
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sequence{" +
                "sequence=" + Arrays.toString(sequence) +
                '}';
    }
}
