package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspIfStmt extends AspCompoundStmt {
    AspExpr expr;
    ArrayList<AspSuite> suiteTests = new ArrayList<>();
    AspSuite elseSuite;

    AspIfStmt(int n) {
        super(n);
    }

    static AspIfStmt parse(Scanner s) {
        AspIfStmt ais = new AspIfStmt(s.curLineNum());
        skip(s, TokenKind.ifToken);

        while(true) {
            ais.expr = AspExpr.parse(s);
            skip(s, TokenKind.colonToken);
            ais.suiteTests.add(AspSuite.parse(s));
            
            if (s.curToken().kind != TokenKind.elifToken) {
                break;
            }
        }

        if(s.curToken().kind == TokenKind.elseToken) {

            skip(s, TokenKind.elseToken);
            skip(s, TokenKind.colonToken);
    
            ais.elseSuite = AspSuite.parse(s);
        }

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
