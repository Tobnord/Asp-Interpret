package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
public class AspBooleanLiteral extends AspAtom {
    boolean isTrueToken = false;
    boolean isFalseToken = false;

    AspBooleanLiteral(int n) {
        super(n);
    }

    static AspBooleanLiteral parse(Scanner s) {
        AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum());
        if (s.curToken().kind == TokenKind.trueToken) {
            skip(s, TokenKind.trueToken)
            abl.isTrueToken = true;
        }
        else if (s.curToken().kind == TokenKind.falseToken) {
            skip(s, TokenKind.falseToken)
            abl.isFalseToken = true;
        }
        return abl;
    }

    @Override
    void prettyPrint(){
        if (isTrueToken) {
            prettyPrint(" true ");
        }
        else if (isFalseToken) {
            prettyPrint(" false ");
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
