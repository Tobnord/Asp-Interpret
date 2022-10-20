package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactorPrefix extends AspSyntax {
    boolean isPlusToken = false;

    AspFactorPrefix(int n) {
        super(n);
    }


    static AspFactorPrefix parse(Scanner s) {
        enterParser("factor prefix");
        AspFactorPrefix afp = new AspFactorPrefix(s.curLineNum());
        if (s.curToken().kind == plusToken) {
            skip(s, TokenKind.plusToken);
            afp.isPlusToken = true;
        }
        else if (s.curToken().kind == minusToken) {
            skip(s, TokenKind.minusToken);
            afp.isPlusToken = false;
        }
        leaveParser("factor prefix");
        return afp;
    }

    @Override
    void prettyPrint() {
        if(isPlusToken) {
            prettyWrite("+");
        }
        else {
            prettyWrite("-");
        }
    }
    
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
