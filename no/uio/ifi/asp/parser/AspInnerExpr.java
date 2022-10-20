package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspInnerExpr extends AspAtom {

    AspExpr expr;

    AspInnerExpr(int n) {
        super(n);
    }

    static AspInnerExpr parse(Scanner s) {
        enterParser("inner expr");
        AspInnerExpr aie = new AspInnerExpr(s.curLineNum());

        
        
        skip(s, TokenKind.leftParToken);
        aie.expr = AspExpr.parse(s);
        skip(s, TokenKind.rightParToken);

        leaveParser("inner expr");
        return aie;
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
