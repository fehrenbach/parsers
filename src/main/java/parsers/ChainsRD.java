package parsers;

import java.util.Arrays;
import java.util.regex.Pattern;

public class ChainsRD {
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

    public ChainsRD(String string) {
        this.string = string.toCharArray();
        this.pos = 0;
    }

    boolean eof() {
        return pos == string.length;
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

    public boolean s() {
        return eof() || c0();
    }

    boolean c0() { return c1(); }
    boolean c1() { return c2(); }
    boolean c2() { return c3(); }
    boolean c3() { return c4(); }
    boolean c4() { return c5(); }
    boolean c5() { return c6(); }
    boolean c6() { return c7(); }
    boolean c7() { return c8(); }
    boolean c8() { return c9(); }
    boolean c9() { return c10(); }
    boolean c10() { return c11(); }
    boolean c11() { return c12(); }
    boolean c12() { return c13(); }
    boolean c13() { return c14(); }
    boolean c14() { return c15(); }
    boolean c15() { return c16(); }
    boolean c16() { return c17(); }
    boolean c17() { return c18(); }
    boolean c18() { return c19(); }
    boolean c19() { return c20(); }
    boolean c20() { return c21(); }
    boolean c21() { return c22(); }
    boolean c22() { return c23(); }
    boolean c23() { return c24(); }
    boolean c24() { return c25(); }
    boolean c25() { return c26(); }
    boolean c26() { return c27(); }
    boolean c27() { return c28(); }
    boolean c28() { return c29(); }
    boolean c29() { return c30(); }
    boolean c30() { return c31(); }
    boolean c31() { return c32(); }
    boolean c32() { return c33(); }
    boolean c33() { return c34(); }
    boolean c34() { return c35(); }
    boolean c35() { return c36(); }
    boolean c36() { return c37(); }
    boolean c37() { return c38(); }
    boolean c38() { return c39(); }
    boolean c39() { return c40(); }
    boolean c40() { return c41(); }
    boolean c41() { return c42(); }
    boolean c42() { return c43(); }
    boolean c43() { return c44(); }
    boolean c44() { return c45(); }
    boolean c45() { return c46(); }
    boolean c46() { return c47(); }
    boolean c47() { return c48(); }
    boolean c48() { return c49(); }
    boolean c49() { return c50(); }
    boolean c50() { return c51(); }
    boolean c51() { return c52(); }
    boolean c52() { return c53(); }
    boolean c53() { return c54(); }
    boolean c54() { return c55(); }
    boolean c55() { return c56(); }
    boolean c56() { return c57(); }
    boolean c57() { return c58(); }
    boolean c58() { return c59(); }
    boolean c59() { return c60(); }
    boolean c60() { return c61(); }
    boolean c61() { return c62(); }
    boolean c62() { return c63(); }
    boolean c63() { return c64(); }
    boolean c64() { return c65(); }
    boolean c65() { return c66(); }
    boolean c66() { return c67(); }
    boolean c67() { return c68(); }
    boolean c68() { return c69(); }
    boolean c69() { return c70(); }
    boolean c70() { return c71(); }
    boolean c71() { return c72(); }
    boolean c72() { return c73(); }
    boolean c73() { return c74(); }
    boolean c74() { return c75(); }
    boolean c75() { return c76(); }
    boolean c76() { return c77(); }
    boolean c77() { return c78(); }
    boolean c78() { return c79(); }
    boolean c79() { return c80(); }
    boolean c80() { return c81(); }
    boolean c81() { return c82(); }
    boolean c82() { return c83(); }
    boolean c83() { return c84(); }
    boolean c84() { return c85(); }
    boolean c85() { return c86(); }
    boolean c86() { return c87(); }
    boolean c87() { return c88(); }
    boolean c88() { return c89(); }
    boolean c89() { return c90(); }
    boolean c90() { return c91(); }
    boolean c91() { return c92(); }
    boolean c92() { return c93(); }
    boolean c93() { return c94(); }
    boolean c94() { return c95(); }
    boolean c95() { return c96(); }
    boolean c96() { return c97(); }
    boolean c97() { return c98(); }
    boolean c98() { return c99(); }
    boolean c99() { return c100(); }
    boolean c100() { return c101(); }
    boolean c101() { return c102(); }
    boolean c102() { return c103(); }
    boolean c103() { return c104(); }
    boolean c104() { return c105(); }
    boolean c105() { return c106(); }
    boolean c106() { return c107(); }
    boolean c107() { return c108(); }
    boolean c108() { return c109(); }
    boolean c109() { return c110(); }
    boolean c110() { return c111(); }
    boolean c111() { return c112(); }
    boolean c112() { return c113(); }
    boolean c113() { return c114(); }
    boolean c114() { return c115(); }
    boolean c115() { return c116(); }
    boolean c116() { return c117(); }
    boolean c117() { return c118(); }
    boolean c118() { return c119(); }
    boolean c119() { return c120(); }
    boolean c120() { return c121(); }
    boolean c121() { return c122(); }
    boolean c122() { return c123(); }
    boolean c123() { return c124(); }
    boolean c124() { return c125(); }
    boolean c125() { return c126(); }
    boolean c126() { return c127(); }
    boolean c127() { return c128(); }
    boolean c128() { return c129(); }
    boolean c129() { return c130(); }
    boolean c130() { return c131(); }
    boolean c131() { return c132(); }
    boolean c132() { return c133(); }
    boolean c133() { return c134(); }
    boolean c134() { return c135(); }
    boolean c135() { return c136(); }
    boolean c136() { return c137(); }
    boolean c137() { return c138(); }
    boolean c138() { return c139(); }
    boolean c139() { return c140(); }
    boolean c140() { return c141(); }
    boolean c141() { return c142(); }
    boolean c142() { return c143(); }
    boolean c143() { return c144(); }
    boolean c144() { return c145(); }
    boolean c145() { return c146(); }
    boolean c146() { return c147(); }
    boolean c147() { return c148(); }
    boolean c148() { return c149(); }
    boolean c149() { return c150(); }
    boolean c150() { return c151(); }
    boolean c151() { return c152(); }
    boolean c152() { return c153(); }
    boolean c153() { return c154(); }
    boolean c154() { return c155(); }
    boolean c155() { return c156(); }
    boolean c156() { return c157(); }
    boolean c157() { return c158(); }
    boolean c158() { return c159(); }
    boolean c159() { return c160(); }
    boolean c160() { return c161(); }
    boolean c161() { return c162(); }
    boolean c162() { return c163(); }
    boolean c163() { return c164(); }
    boolean c164() { return c165(); }
    boolean c165() { return c166(); }
    boolean c166() { return c167(); }
    boolean c167() { return c168(); }
    boolean c168() { return c169(); }
    boolean c169() { return c170(); }
    boolean c170() { return c171(); }
    boolean c171() { return c172(); }
    boolean c172() { return c173(); }
    boolean c173() { return c174(); }
    boolean c174() { return c175(); }
    boolean c175() { return c176(); }
    boolean c176() { return c177(); }
    boolean c177() { return c178(); }
    boolean c178() { return c179(); }
    boolean c179() { return c180(); }
    boolean c180() { return c181(); }
    boolean c181() { return c182(); }
    boolean c182() { return c183(); }
    boolean c183() { return c184(); }
    boolean c184() { return c185(); }
    boolean c185() { return c186(); }
    boolean c186() { return c187(); }
    boolean c187() { return c188(); }
    boolean c188() { return c189(); }
    boolean c189() { return c190(); }
    boolean c190() { return c191(); }
    boolean c191() { return c192(); }
    boolean c192() { return c193(); }
    boolean c193() { return c194(); }
    boolean c194() { return c195(); }
    boolean c195() { return c196(); }
    boolean c196() { return c197(); }
    boolean c197() { return c198(); }
    boolean c198() { return c199(); }
    boolean c199() { return c200(); }
    boolean c200() { return e(); }

    boolean e() { return character('a') && s(); }

    public static void main(String[] args) {
        ChainsRD parser = new ChainsRD("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(parser.s());
    }

    public void reset() {
        this.pos = 0;
    }
}


