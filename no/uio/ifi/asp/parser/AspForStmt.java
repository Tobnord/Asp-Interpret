package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspForStmt extends AspCompoundStmt {
    AspName name;
    AspExpr expr;
    AspSuite suite;

    AspForStmt(int n) {
        super(n);
    }

    static AspForStmt parse(Scanner s) {
        enterParser("for stmt");
        AspForStmt aft = new AspForStmt(s.curLineNum());

        skip(s, TokenKind.forToken);
        aft.name = AspName.parse(s);
        skip(s, TokenKind.inToken);
        aft.expr = AspExpr.parse(s);
        skip(s, TokenKind.colonToken);
        aft.suite = AspSuite.parse(s);
        
        leaveParser("for stmt");
        return aft; 
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }

    @Override
    void prettyPrint(){
        
    }
}
