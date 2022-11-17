package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.main.*;
public class AspFactorPrefix extends AspSyntax {
    boolean isPlusToken = false;
    TokenKind kind;

    AspFactorPrefix(int n) {
        super(n);
    }

    @Override
    public String toString() {
        if(isPlusToken) {
            return("+");
        }
        else {
            return("-");
        }
    }


    static AspFactorPrefix parse(Scanner s) {
        enterParser("factor prefix");
        AspFactorPrefix afp = new AspFactorPrefix(s.curLineNum());
        if (s.curToken().kind == plusToken) {
            skip(s, TokenKind.plusToken);
            afp.isPlusToken = true;
            afp.kind = plusToken;
        }
        else if (s.curToken().kind == minusToken) {
            skip(s, TokenKind.minusToken);
            afp.isPlusToken = false;
            afp.kind = minusToken;
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
        //System.out.println("EVAL: Factor prefix");
        return null;
    }
    
}
