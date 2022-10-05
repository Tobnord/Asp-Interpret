package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
public class AspIfStmt extends AspCompoundStmt {
    // COPY PASTE - MUST EDIT THE BODY
    AspExpr test;
    AspSuite body;

    AspIfStmt(int n) {
        super(n);
    }

    static AspIfStmt parse(Scanner s) {
        AspIfStmt ais = new AspIfStmt(s.curLineNum());
        skip(s, TokenKind.ifToken);
        ais.test = AspExpr.parse(s);
        skip(s, TokenKind.colonToken);
        ais.body = AspSuite.parse(s);
        return ais;
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
