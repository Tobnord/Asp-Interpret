package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspPrimary extends AspSyntax {
    AspAtom atomTest;
    ArrayList<AspPrimarySuffix> primarySuffixTests = new ArrayList<>();

    AspPrimary(int n) {
        super(n);
    }

    public static AspPrimary parse(Scanner s) {
        enterParser("primary");
        AspPrimary ap = new AspPrimary(s.curLineNum());

        ap.atomTest = AspAtom.parse(s);

        while(true) {
            if (s.curToken().kind != TokenKind.leftParToken && s.curToken().kind != TokenKind.leftBracketToken) {
                break;
            }

            ap.primarySuffixTests.add(AspPrimarySuffix.parse(s));
        }

        leaveParser("primary");
        return ap;
    }

    @Override
    void prettyPrint() {
        this.atomTest.prettyPrint();
        for (AspPrimarySuffix aspPrimarySuffix : this.primarySuffixTests) {
            aspPrimarySuffix.prettyPrint();
        }
    }
    
    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //System.out.println("EVAL: Primary");
        
        RuntimeValue v = atomTest.eval(curScope);
        
        for (AspPrimarySuffix aspPrimarySuffix : primarySuffixTests) {
            v = aspPrimarySuffix.eval(curScope);
        }

        return v;
    }

}
