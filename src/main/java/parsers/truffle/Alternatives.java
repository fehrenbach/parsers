package parsers.truffle;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;

import java.util.Arrays;

public class Alternatives extends GrammarNode {
    private final @Children Sequence[] alternatives;

    public Alternatives(ParserState p, Sequence... alternatives) {
        super(p);
        this.alternatives = alternatives;
    }

    @Override @ExplodeLoop
    public boolean executeParse(VirtualFrame frame) {
        for (Sequence alternative : alternatives) {
            if (alternative.executeParse(frame))
                return true;
            else
                continue;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Alternatives{" +
                "alternatives=" + Arrays.toString(alternatives) +
                '}';
    }
}
