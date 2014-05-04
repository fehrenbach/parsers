package org.morphling.parsers.truffle;

public class Sequence extends GrammarNode {
    private final GrammarPrimitive[] sequence;

    public Sequence(GrammarPrimitive... sequence) {
        this.sequence = sequence;
    }
}
