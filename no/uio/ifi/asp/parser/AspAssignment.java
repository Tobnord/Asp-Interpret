package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspAssignment extends AspSmallStmt {

    AspName name;
    ArrayList<AspSubscriptions> subscriptionsTests = new ArrayList<>();
    AspExpr expr;

    AspAssignment(int n) {
        super(n);
    }

    static AspAssignment parse(Scanner s) {
        enterParser("assignment");
        AspAssignment aa = new AspAssignment(s.curLineNum());

        aa.name = AspName.parse(s);

        if (s.curToken().kind != TokenKind.equalToken) {
            while(true) {
                aa.subscriptionsTests.add(AspSubscriptions.parse(s));
                if (s.curToken().kind == TokenKind.equalToken) {
                    break;
                }
            }
        }
        
        skip(s, TokenKind.equalToken);

        aa.expr = AspExpr.parse(s);

        leaveParser("assignment");
        return aa;
    }

    @Override
    void prettyPrint(){
        
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
