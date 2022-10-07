package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSubscriptions extends AspPrimarySuffix {
    AspExpr exprTest;

    AspSubscriptions(int n) {
        super(n);
    }

    static AspSubscriptions parse(Scanner s) {
        enterParser("subscription");
        AspSubscriptions as = new AspSubscriptions(s.curLineNum());

        skip(s, TokenKind.leftBracketToken);
        as.exprTest = AspExpr.parse(s);
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
