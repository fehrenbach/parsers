package parsers.truffle;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives.SlowPath;
import com.oracle.truffle.api.Truffle;

import java.util.HashMap;

public class ParserState {
    private final char[] string;
    private int currentIndex = 0;
    private final HashMap<NonterminalName, CallTarget> grammar = new HashMap<>();
    private CallTarget ct;
    private final HashMap<NonterminalName, Assumption> cachedNonterminals = new HashMap<>();

    public ParserState(String string) {
        this.string = string.toCharArray();
    }

    public void addProduction(NonterminalName name, Alternatives p) {
        Assumption cachedNonterminalAssumption = cachedNonterminals.get(name);
        if (cachedNonterminalAssumption != null) {
            cachedNonterminalAssumption.invalidate();
        }
        grammar.put(name, Truffle.getRuntime().createCallTarget(new ParserRootNode(p)));
    }

    @SlowPath
    public CallTarget lookupInEnv(NonterminalName nonterminalName) {
        return grammar.get(nonterminalName);
    }

    public int getCurrentChar() {
        return string[currentIndex];
    }

    public boolean atEOF() {
        return currentIndex == string.length;
    }

    public void incCurrentChar() {
        currentIndex++;
    }

    public Object parse(NonterminalName startSymbol) {
        if (ct == null) {
            ct = grammar.get(startSymbol);
        }
        return ct.call();
    }

    public static void main(String[] args) {
        ParserState p = new ParserState("aaaaaa");
        p.addProduction(new NonterminalName("S"),
                new Alternatives(p,
                        new Sequence(p, new EOF(p)),
                        new Sequence(p, new TerminalSymbol(p, 'a'),
                                new UninitializedNonterminalCall(p, new NonterminalName("S")))
                ));
        Object parseResult = p.parse(new NonterminalName("S"));
        System.out.println(parseResult);
    }

    public void resetParserState() {
        currentIndex = 0;
    }

    public Assumption cacheNonterminal(NonterminalName nonterminalName)  {
        Assumption assumption = Truffle.getRuntime().createAssumption(nonterminalName.toString());
        cachedNonterminals.put(nonterminalName, assumption);
        return assumption;
    }
}
