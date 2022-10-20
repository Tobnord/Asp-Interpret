package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspSubscriptions extends AspPrimarySuffix {
    AspExpr expr;

    AspSubscriptions(int n) {
        super(n);
    }

    static AspSubscriptions parse(Scanner s) {
        enterParser("subscription");
        AspSubscriptions as = new AspSubscriptions(s.curLineNum());

        skip(s, TokenKind.leftBracketToken);
        as.expr = AspExpr.parse(s);
        skip(s, TokenKind.rightBracketToken);

        leaveParser("subscription");
        return as;
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
