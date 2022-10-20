package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSuite extends AspSyntax {

    ArrayList<AspStmt> stmtTests = new ArrayList<>();
    static AspSmallStmtList smallStmtList;

    AspSuite(int n) {
        super(n);
    }

    static AspSuite parse(Scanner s) {
        enterParser("Suite");
        AspSuite as = new AspSuite(s.curLineNum());

        if (s.curToken().kind == TokenKind.newLineToken) {

            skip(s, TokenKind.newLineToken);
            skip(s, TokenKind.indentToken);

            while(true) {
                as.stmtTests.add(AspStmt.parse(s));
                if (s.curToken().kind == TokenKind.dedentToken) {
                    break;
                }
            }

            skip(s, TokenKind.dedentToken);
        }
        else {
            smallStmtList = AspSmallStmtList.parse(s);
        }

        leaveParser("Suite");
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
