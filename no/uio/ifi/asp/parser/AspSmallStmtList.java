package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspSmallStmtList extends AspStmt {
    ArrayList<AspSmallStmt> smallStmtTest = new ArrayList<>();
    int numberOfSemicolon = 0;
    boolean terminatingSemicolon = false;

    AspSmallStmtList(int n) {
        super(n);
    }

    static AspSmallStmtList parse(Scanner s) {
        enterParser("small stmt list");
        AspSmallStmtList assl = new AspSmallStmtList(s.curLineNum());

        while(true) {
            assl.smallStmtTest.add(AspSmallStmt.parse(s));
            if (s.curToken().kind == TokenKind.newLineToken) {
                assl.terminatingSemicolon = false;
                break;
            }
            skip(s, TokenKind.semicolonToken);
            
            if (s.curToken().kind == TokenKind.newLineToken) {
                assl.terminatingSemicolon = true;
                break;
            }
        }
        skip(s, TokenKind.newLineToken);

        leaveParser("small stmt list");
        return assl;
    }

    @Override
    void prettyPrint() {

        // Writing a semicolon for each small stmt except for the last.
        // For the last small stmt there is a check for if it should be a
        // terminating semicolon.

        int counter = 0;
        for (AspSmallStmt aspSmallStmt : smallStmtTest) {
            aspSmallStmt.prettyPrint();
            counter++;
            if (counter < smallStmtTest.size()) {
                prettyWrite("; ");
            }
        }

        if (this.terminatingSemicolon) {
            prettyWrite(";");
        }
        prettyWriteLn();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
