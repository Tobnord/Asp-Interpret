package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspSmallStmt extends AspSyntax {
    AspSmallStmt(int n) {
        super(n);
    }

    static AspSmallStmt parse(Scanner s) {
        AspSmallStmt as = null;
        TokenKind cur = s.curToken().kind;
        if (cur == TokenKind.globalToken)
            as = AspGlobalStmt.parse(s);
        else if (cur == TokenKind.passToken)
            as = AspPassStmt.parse(s);
        else if (cur == TokenKind.returnToken)
            as = AspReturnStmt.parse(s);
        else if (cur == TokenKind.nameToken && s.anyEqualToken())
            as = AspAssignment.parse(s);
        else
            as = AspExprStmt.parse(s);
        return as;
    }
}