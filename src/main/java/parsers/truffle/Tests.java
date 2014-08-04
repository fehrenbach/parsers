package parsers.truffle;

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

    public static String repeat(char c, int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        UninitializedNonterminalCall.callNodeType = UninitializedNonterminalCall.CallNodeType.UNOPTIMIZED;
        long unoptimized = timeChainedProductions(150, 150, 10000, 5000);

        UninitializedNonterminalCall.callNodeType = UninitializedNonterminalCall.CallNodeType.CACHED;
        long call = timeChainedProductions(150, 150, 10000, 5000);

        System.out.println("Unoptimized: " + unoptimized);
        System.out.println("Cached call: " + call);
    }

    private static long timeChainedProductions(int stringLength, int chainLength, int warmUpIterations, int parseIterations) {
        ParserState p = new ParserState(repeat('a', stringLength));
        NonterminalName startSymbol = createChainedProductions(p, chainLength);

        boolean foo = false;
        for (int i = 0; i < warmUpIterations; i++) {
            foo |= (Boolean) p.parse(startSymbol);
            p.resetParserState();
        }

        System.out.println("finished warm up, hopefully, after " + warmUpIterations + " iterations");

        long time = System.currentTimeMillis();

        for (int i = 0; i < parseIterations; i++) {
            foo |= (Boolean) p.parse(startSymbol);
            p.resetParserState();
        }

        time = System.currentTimeMillis() - time;

        System.out.println("Do not optimize my code away: " + foo);

        return time;
    }

    public static NonterminalName createChainedProductions(ParserState p, int chainLength) {
        NonterminalName startSymbol = new NonterminalName("S");
        NonterminalName endOfChain = new NonterminalName("E");
        NonterminalName startOfChain = chainedProductions(p, endOfChain, chainLength);
        p.addProduction(startSymbol, new Alternatives(p,
                new Sequence(p, new EOF(p)),
                new Sequence(p, new UninitializedNonterminalCall(p, startOfChain))));
        p.addProduction(endOfChain, new Alternatives(p, new Sequence(p, new TerminalSymbol(p, 'a'), new UninitializedNonterminalCall(p, startSymbol))));
        return startSymbol;
    }

    public static NonterminalName createChainedProductionsOuter(ParserState p, int chainLength) {
        NonterminalName startSymbol = new NonterminalName("S");
        NonterminalName endOfChain = new NonterminalName("E");
        NonterminalName startOfChain = chainedProductions(p, endOfChain, chainLength);
        p.addProduction(startSymbol, new Alternatives(p,
                new Sequence(p, new EOF(p)),
                new Sequence(p, new UninitializedNonterminalCall(p, startOfChain), new UninitializedNonterminalCall(p, startSymbol))));
        p.addProduction(endOfChain, new Alternatives(p, new Sequence(p, new TerminalSymbol(p, 'a'))));
        return startSymbol;
    }
}
