package org.morphling.parsers;

import java.util.Arrays;
import java.util.regex.Pattern;

class RecursiveDescent {
    /* Grammar:
        S := SExp <EOF>
        SExp = Atom | Pair
        Atom := Symbol | Number
        Pair := '(' SExp '.' SExp ')'
        Symbol := [a-zA-Z]+
        Number := [0-9]+
     */

    char[] string;
    int pos;

    RecursiveDescent(String string) {
        this.string = string.toCharArray();
        this.pos = 0;
    }

    boolean s() {
        return sexp() && eof();
    }

    boolean sexp() {
        return atom() || pair();
    }

    boolean eof() {
        return pos == string.length;
    }

    boolean atom() {
        return symbol() || number();
    }

    boolean pair() {
        return character('(') && sexp() && character('.') && sexp() && character(')');
    }

    boolean symbol() {
        return regex("[a-zA-Z]+");
    }

    boolean number() {
        return regex("[0-9]+");
    }

    boolean character(char c) {
        if (string[pos] == c) {
            pos++;
            return true;
        }
        return false;
    }

    boolean regex(String regex) {
        Pattern p = Pattern.compile(regex);
        String s = new String(Arrays.copyOfRange(string, pos, string.length));
        String[] r = p.split(s, 2);
        // pattern doesn't match
        if (r.length == 1) return false;
        String before = r[0];
        // pattern doesn't match at the beginning
        // TODO maybe put beginning of string-thingy in front of the pattern string?
        if (before.length() != 0) return false;
        String after = r[1];
        int matchedLength = s.length() - after.length();
        pos += matchedLength;
        return true;
    }

    public static void main(String[] args) {
        RecursiveDescent parser = new RecursiveDescent("(x.(y.(z.NIL)))");
        System.out.println(parser.s());
    }
}