package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspArguments extends AspPrimarySuffix {

    ArrayList<AspExpr> exprTests = new ArrayList<>();

    AspArguments(int n) {
        super(n);
    }

    
    static AspArguments parse(Scanner s) {
        enterParser("argument");
        AspArguments aa = new AspArguments(s.curLineNum());

        skip(s, TokenKind.leftParToken);
        
        if (s.curToken().kind != TokenKind.rightParToken) {

            while (true) {
                aa.exprTests.add(AspExpr.parse(s));
    
                if (s.curToken().kind == TokenKind.rightParToken) {
                    break;
                }
    
                skip(s, TokenKind.commaToken);            
            }
        }

        skip(s, TokenKind.rightParToken);
        leaveParser("argument");
        return aa;
    }

    @Override
    void prettyPrint(){

        prettyWrite("(");

        if (this.exprTests.size() != 0) {
            int counter = 0;
            for (AspExpr expr : exprTests) {
                expr.prettyPrint();
                counter++;
                if (this.exprTests.size() != counter) {
                    prettyWrite(",");
                }
            }
        }
        prettyWrite(")");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //System.out.println("EVAL: Arguments");

        RuntimeValue v = exprTests.get(0).eval(curScope);

        for (int i = 1; i < exprTests.size(); ++i) {
            v = exprTests.get(i).eval(curScope);
        }
        
        return v;
    }
}
