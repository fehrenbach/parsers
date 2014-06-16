package org.morphling.parsers.truffle;

public class Tests {
    private static NonterminalName getChainName(int i) {
        return new NonterminalName(Integer.toString(i));
    }

    static NonterminalName chainedProductions(ParserState p, NonterminalName end, int n) {
        for (int i = 0; i < n; i++) {
            p.addProduction(getChainName(i), new Alternatives(p, new Sequence(p, new UninitializedNonterminalCall(p, getChainName(i+1)))));
        }
        p.addProduction(getChainName(n), new Alternatives(p, new Sequence(p, new UninitializedNonterminalCall(p, end))));
        return getChainName(0);
    }

    private static String repeat(char c, int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        NonterminalName startSymbol = new NonterminalName("S");
        ParserState p = new ParserState(repeat('a', 100));
        NonterminalName endOfChain = new NonterminalName("E");
        NonterminalName startOfChain = chainedProductions(p, endOfChain, 100);
        p.addProduction(startSymbol, new Alternatives(p,
                new Sequence(p, new EOF(p)),
                new Sequence(p, new UninitializedNonterminalCall(p, startOfChain))));
        p.addProduction(endOfChain, new Alternatives(p, new Sequence(p, new TerminalSymbol(p, 'a'), new UninitializedNonterminalCall(p, startSymbol))));

        boolean foo = false;
        for (int i = 0; i < 5000; i++) {
            foo |= (Boolean) p.parse(startSymbol);
            p.resetParserState();
        }

        System.out.println("finished warm up, hopefully.");

        long time = System.currentTimeMillis();

        for (int i = 0; i < 5000; i++) {
            foo |= (Boolean) p.parse(startSymbol);
            p.resetParserState();
        }

        System.out.println(foo);

        System.out.println((System.currentTimeMillis() - time) + " ms");
    }
}
