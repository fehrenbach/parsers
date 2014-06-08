package org.morphling.parsers.truffle;

import com.oracle.truffle.api.Truffle;

import java.util.HashMap;

public class ParserState {
    private final String string;
    private int currentIndex = 0;
    private final HashMap<NonterminalName, Alternatives> grammar = new HashMap<>();
    private final NonterminalName startSymbol;

    public ParserState(String string, NonterminalName startSymbol) {
        this.string = string;
        this.startSymbol = startSymbol;
    }

    public void addProduction(NonterminalName name, Alternatives p) {
        grammar.put(name, p);
    }

    public Alternatives lookupInEnv(NonterminalName nonterminalName) {
        return grammar.get(nonterminalName);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public String getString() {
        return string;
    }

    public int getCurrentChar() {
        int currentIndex = getCurrentIndex();
        int codePoint = getString().codePointAt(currentIndex);
        return codePoint;
    }

    public void incCurrentChar() {
        currentIndex++;
    }

    public Object parse() {
        return Truffle.getRuntime().createCallTarget(new ParserRootNode(grammar.get(startSymbol))).call();
    }

    public static void main(String[] args) {
        ParserState p = new ParserState("aaaaaa", new NonterminalName("S"));
        p.addProduction(new NonterminalName("S"),
                new Alternatives(p,
                        new Sequence(p, new EOF(p)),
                        new Sequence(p, new TerminalSymbol(p, 'a'),
                                new UninitializedNonterminalCall(p, new NonterminalName("S")))
                ));
        Object parseResult = p.parse();
        p.grammar.get(p.startSymbol);
        System.out.println(parseResult);
    }
}
