package parsers.interpreter;

public class Alt extends Node {
    private final Node[] nodes;

    public Alt(Node... nodes) {
        this.nodes = nodes;
    }

    @Override
    boolean execute(ParserState ps) {
        for (Node node : nodes) {
            if (node.execute(ps)) {
                return true;
            }
        }
        return false;
    }
}
