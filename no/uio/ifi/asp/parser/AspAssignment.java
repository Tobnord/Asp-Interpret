package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

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
        this.name.prettyPrint();
        for (AspSubscriptions subscription : this.subscriptionsTests) {
            subscription.prettyPrint();
        }
        prettyWrite(" = ");
        this.expr.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
