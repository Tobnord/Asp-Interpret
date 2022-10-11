package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSmallStmtList extends AspStmt {
    ArrayList<AspSmallStmt> smallStmtTest = new ArrayList<>();

    AspSmallStmtList(int n) {
        super(n);
    }

    static AspSmallStmtList parse(Scanner s) {
        enterParser("small stmt list");
        AspSmallStmtList assl = new AspSmallStmtList(s.curLineNum());

        while(true) {
            assl.smallStmtTest.add(AspSmallStmt.parse(s));
            if (s.curToken().kind == TokenKind.newLineToken) {
                break;
            }
            skip(s, TokenKind.semicolonToken);
            if (s.curToken().kind == TokenKind.newLineToken) {
                break;
            }
        }
        skip(s, TokenKind.newLineToken);

        leaveParser("small stmt list");
        return assl;
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
