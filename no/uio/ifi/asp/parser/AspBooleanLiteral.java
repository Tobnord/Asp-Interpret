package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
public class AspBooleanLiteral extends AspAtom {
    TokenKind booleanLiteral;
    boolean value;

    AspBooleanLiteral(int n) {
        super(n);
    }

    static AspBooleanLiteral parse(Scanner s) {
        enterParser("boolean literal");
        AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum());
        if (s.curToken().kind == TokenKind.trueToken) {
            skip(s, TokenKind.trueToken);
            abl.booleanLiteral = TokenKind.trueToken;
            abl.value = true;
        }
        else if (s.curToken().kind == TokenKind.falseToken) {
            skip(s, TokenKind.falseToken);
            abl.booleanLiteral = TokenKind.falseToken;
            abl.value = false;
        }
        else {
            abl.booleanLiteral = null;
            test(s, TokenKind.trueToken, TokenKind.falseToken);
        }
        leaveParser("boolean literal");
        return abl;
    }

    @Override
    void prettyPrint(){
        if (booleanLiteral == TokenKind.trueToken) {
            prettyWrite("True");
        }
        else if (booleanLiteral == TokenKind.falseToken) {
            prettyWrite("False");
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        System.out.println("EVAL: Boolean");
        return new RuntimeBoolValue(value);
    }
}