package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspForStmt extends AspCompoundStmt {
    // COPY PASTE - MUST EDIT THE BODY
    AspExpr test;
    AspSuite body;

    AspForStmt(int n) {
        super(n);
    }

    static AspForStmt parse(Scanner s) {
        AspForStmt aft = new AspForStmt(s.curLineNum());
        skip(s, TokenKind.forToken);
        aft.test = AspExpr.parse(s);
        skip(s, TokenKind.colonToken);
        aft.body = AspSuite.parse(s);
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
