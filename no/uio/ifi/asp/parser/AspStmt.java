package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspStmt extends AspSyntax{


    AspStmt(int n) {
        super(n);
    }

    static AspStmt parse(Scanner s) {
        enterParser("stmt");
        AspStmt as = null;
        if (
            s.curToken().kind == TokenKind.defToken ||
            s.curToken().kind == TokenKind.forToken ||
            s.curToken().kind == TokenKind.ifToken  ||
            s.curToken().kind == TokenKind.whileToken
        ) {
            as = AspCompoundStmt.parse(s);
        }
        else {
            as = AspSmallStmtList.parse(s);
        }
        leaveParser("stmt");
        return as;
    }

    @Override
    void prettyPrint() {
        
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
