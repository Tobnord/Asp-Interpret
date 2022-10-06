package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspListDisplay extends AspAtom {
    ArrayList<AspExpr> expressionList = new ArrayList<>();


    AspListDisplay(int n) {
        super(n);
    }

    static AspListDisplay parse(Scanner s) {
        enterParser("list display");
        AspListDisplay ald = new AspListDisplay(s.curLineNum());

        skip(s, TokenKind.leftBracketToken);

        if (s.curToken().kind != TokenKind.rightBracketToken) {

            while(true) {
                if (s.curToken().kind == TokenKind.commaToken) {
                    skip(s, commaToken);
                }

                ald.expressionList.add(AspExpr.parse(s));

                if (s.curToken().kind != TokenKind.rightBracketToken) {
                    break;
                }
            }
        }

        skip(s, TokenKind.rightBracketToken);

        leaveParser("and test");
        return ald;
    }
    
    @Override
    void prettyPrint() {
        int nPrinted = 0;
        for (AspExpr ae : expressionList) {
            ae.prettyPrint();

            ++nPrinted;
            if (nPrinted < dictDisplayHashMap.size()) {
                prettyWrite(", ");
            }
        }
    }
}
