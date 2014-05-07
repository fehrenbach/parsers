package org.morphling.parsers.truffle;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleRuntime;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import org.morphling.parsers.interpreter.*;

import java.lang.Character;
import java.util.HashMap;

/* Context free grammar:

    Production rules of the form

    V -> alpha | beta | ...

    Where
     V is a nonterminal symbol
     the right hand side is a list of alternatives
     every alternative (alpha, beta, ...) is a sequence of terminal and nonterminal symbols

 */


public class Parser {
    final HashMap<NonterminalName, Alternatives> grammar = new HashMap<>();
    NonterminalName startSymbol;
    final String string;

    public TruffleRuntime truffleRuntime = Truffle.getRuntime();
    public FrameDescriptor rootFrameDescriptor = new FrameDescriptor();
    public FrameSlot currentIndexSlot = rootFrameDescriptor.addFrameSlot("currentIndex", FrameSlotKind.Int);
    public FrameSlot stringSlot = rootFrameDescriptor.addFrameSlot("stringSlot", FrameSlotKind.Object);
    public FrameSlot environmentSlot = rootFrameDescriptor.addFrameSlot("environmentSlot", FrameSlotKind.Object);

    public Parser(String s) {
        this.string = s;
    }

    public void addProduction(NonterminalName name, Alternatives p) {
        grammar.put(name, p);
    }
    public void setStartSymbol(NonterminalName name) {
        startSymbol = name;
    }

    private class ParsingRootNode extends RootNode {
        private final Parser p;

        ParsingRootNode(FrameDescriptor fd, Parser p) {
            super(null, fd);
            this.p = p;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            frame.setInt(p.currentIndexSlot, 0);
            frame.setObject(p.stringSlot, p.string);
            frame.setObject(p.environmentSlot, p.grammar);
            return p.grammar.get(p.startSymbol).executeParse(frame);
        }
    }

    public Object parse() {
        ParsingRootNode rootNode = new ParsingRootNode(rootFrameDescriptor, this);
        return truffleRuntime.createCallTarget(rootNode).call();
    }

    public static void main(String[] args) {
        Parser p = new Parser("a");
        p.addProduction(new NonterminalName("S"), new Alternatives(new Sequence(new TerminalSymbol('a', p.currentIndexSlot, p.stringSlot))));
        p.setStartSymbol(new NonterminalName("S"));
        Object parseResult = p.parse();
        System.out.println(parseResult);
    }
}
