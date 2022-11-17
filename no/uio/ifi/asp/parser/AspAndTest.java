package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

class AspAndTest extends AspSyntax {
    ArrayList<AspNotTest> notTests = new ArrayList<>();

    AspAndTest(int n) {
        super(n);
    }

    @Override
    public String toString() {
        String returnString = "";
        for (AspNotTest aspNotTest : notTests) {
            returnString += aspNotTest.toString();
        }
        return returnString;
    }

    static AspAndTest parse(Scanner s) {
        enterParser("and test");
        AspAndTest aat = new AspAndTest(s.curLineNum());
        while (true) {
            aat.notTests.add(AspNotTest.parse(s));
            if (s.curToken().kind != TokenKind.andToken)
                break;
            skip(s, TokenKind.andToken);
        }
        leaveParser("and test");
        return aat;
    }

    @Override
    void prettyPrint() {
        int nPrinted = 0;
        for (AspNotTest ant : notTests) {
            if (nPrinted > 0)
                prettyWrite(" and ");
            ant.prettyPrint();
            ++nPrinted;
        }
    }
    
    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //System.out.println("EVAL: AspAnd");
        RuntimeValue v = notTests.get(0).eval(curScope);
        for (int i = 1; i < notTests.size(); ++i) {
            if (! v.getBoolValue("and operand",this))
                return v;
            v = notTests.get(i).eval(curScope);
        }
        return v;
    }
}