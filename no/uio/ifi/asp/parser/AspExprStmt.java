package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspExprStmt extends AspSmallStmt {

    AspExpr expr;

    AspExprStmt(int n) {
        super(n);
    }

    @Override
    public String toString() {
        return expr.toString();
    }

    static AspExprStmt parse(Scanner s) {
        enterParser("expr stmt");
        AspExprStmt aes = new AspExprStmt(s.curLineNum());

        aes.expr = AspExpr.parse(s);

        leaveParser("expr stmt");
        return aes;
    }

    @Override
    void prettyPrint(){
        expr.prettyPrint();
    }
    
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return expr.eval(curScope);
    }
}
