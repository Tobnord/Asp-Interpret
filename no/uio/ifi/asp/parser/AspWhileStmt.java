package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspWhileStmt extends AspCompoundStmt {
    AspExpr test;
    AspSuite body;

    AspWhileStmt(int n) {
        super(n);
    }

    static AspWhileStmt parse(Scanner s) {
        enterParser("while stmt");
        AspWhileStmt aws = new AspWhileStmt(s.curLineNum());
        skip(s, TokenKind.whileToken);
        aws.test = AspExpr.parse(s);
        skip(s, TokenKind.colonToken);
        aws.body = AspSuite.parse(s);
        leaveParser("while stmt");
        return aws;
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }

    @Override
    void prettyPrint() {
        
    }
}
