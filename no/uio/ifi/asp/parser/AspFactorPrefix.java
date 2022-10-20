package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
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
        AspFactorPrefix ato = new AspFactorPrefix(s.curLineNum());
        if (s.curToken().kind == plusToken) {
            skip(s, TokenKind.plusToken);
            ato.isPlusToken = true;
        }
        else if (s.curToken().kind == minusToken) {
            skip(s, TokenKind.minusToken);
            ato.isPlusToken = false;
        }
        leaveParser("factor prefix");
        return ato;
    }

    @Override
    void prettyPrint() {
        if(isPlusToken) {
            prettyWrite(" + ");
        }
        else {
            prettyWrite(" - ");
        }
    }
    
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
