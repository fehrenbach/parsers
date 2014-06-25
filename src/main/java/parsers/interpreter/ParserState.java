package parsers.interpreter;

import java.util.Arrays;
import java.util.HashMap;

public class ParserState {

    private int pos;
    private final char[] string;
    private final HashMap<String, Node> productions;

    public ParserState(String s) {
        pos = 0;
        string = s.toCharArray();
        productions = new HashMap<>();
    }

    public int pos() {
        return pos;
    }

    public char charAt(int pos) {
        return string[pos];
    }

    public void incPos() {
        pos++;
    }

    public void incPos(int inc) {
        pos += inc;
    }

    public String rest() {
        return new String(Arrays.copyOfRange(string, pos, string.length));
    }

    public boolean atEnd() {
        return pos == string.length;
    }

    public void addProduction(String name, Node grammar) {
        productions.put(name, grammar);
    }

    public Node lookup(String name) {
        return productions.get(name);
    }
}
