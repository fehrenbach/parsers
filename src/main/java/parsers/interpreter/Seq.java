package parsers.interpreter;

public class Seq extends Node {

    private final Node[] nodes;

    public Seq(Node... nodes) {
        this.nodes = nodes;
    }

    @Override
    boolean execute(ParserState ps) {
        for (Node node : nodes) {
            if (!node.execute(ps)) {
                return false;
            }
        }
        return true;
    }
}
