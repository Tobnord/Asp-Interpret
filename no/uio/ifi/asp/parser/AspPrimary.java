package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

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
        
    }
    
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
