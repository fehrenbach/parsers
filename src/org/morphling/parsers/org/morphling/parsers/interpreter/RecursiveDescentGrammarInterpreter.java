package org.morphling.parsers.org.morphling.parsers.interpreter;

public class RecursiveDescentGrammarInterpreter {

    public static void main(String[] args) {
        /* Grammar:
            S := SExp <EOF>
            SExp := Atom | Pair
            Atom := Symbol | Number
            Pair := '(' SExp '.' SExp ')'
            Symbol := [a-zA-Z]+
            Number := [0-9]+
        */
        ParserState ps = new ParserState("(x.(y.(z.NIL)))");
        Node s = new Seq(new Production("sexp"), new Eof());
        ps.addProduction("s", s);
        ps.addProduction("sexp", new Alt(new Production("atom"), new Production("pair")));
        ps.addProduction("atom", new Alt(new Production("symbol"), new Production("number")));
        ps.addProduction("pair", new Seq(new Character('('), new Production("sexp"), new Character('.'), new Production("sexp"), new Character(')')));
        ps.addProduction("symbol", new Regex("[a-zA-Z]+"));
        ps.addProduction("number", new Regex("[0-9]+"));

        System.out.println(s.execute(ps));
    }
}
