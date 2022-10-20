package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspReturnStmt extends AspSmallStmt{

    AspExpr expr;

    AspReturnStmt(int n) {
        super(n);
    }

    static AspReturnStmt parse(Scanner s) {
        enterParser("return statement");
        AspReturnStmt ars = new AspReturnStmt(s.curLineNum());

        skip(s, TokenKind.returnToken);
        ars.expr = AspExpr.parse(s);

        leaveParser("return statement");
        return ars;
    }

    @Override
    void prettyPrint() {
        prettyWrite("return ");
        this.expr.prettyPrint();
    }
    
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
