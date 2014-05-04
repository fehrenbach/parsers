package org.morphling.parsers.truffle;

public class Alternatives extends GrammarNode {
    private final Sequence[] alternatives;

    public Alternatives(Sequence... alternatives) {
        this.alternatives = alternatives;
    }
}
