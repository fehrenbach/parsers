package org.morphling.parsers.truffle;

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

    public void addProduction(NonterminalName name, Alternatives p) {
        grammar.put(name, p);
    }

    public boolean parse(String s) {
        // TODO
        return false;
    }
}
