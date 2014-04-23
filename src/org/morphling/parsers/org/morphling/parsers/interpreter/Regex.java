package org.morphling.parsers.org.morphling.parsers.interpreter;

import java.util.regex.Pattern;

public class Regex extends Node {


    private final Pattern p;

    public Regex(String regex) {
        this.p = Pattern.compile(regex);
    }

    @Override
    boolean execute(ParserState ps) {
        String s = ps.rest();
        String[] r = p.split(s, 2);
        // pattern doesn't match
        if (r.length == 1) return false;
        String before = r[0];
        // pattern doesn't match at the beginning
        // TODO maybe put beginning of string-thingy in front of the pattern string?
        if (before.length() != 0) return false;
        String after = r[1];
        int matchedLength = s.length() - after.length();
        ps.incPos(matchedLength);
        return true;
    }
}
