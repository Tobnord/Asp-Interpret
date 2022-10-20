package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspPassStmt extends AspSmallStmt{

    AspPassStmt(int n) {
        super(n);
    }

    static AspPassStmt parse(Scanner s) {
        enterParser("pass statement");
        AspPassStmt aps = new AspPassStmt(s.curLineNum());
        skip(s, TokenKind.newLineToken);

        leaveParser("pass statement");
        return aps;
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
