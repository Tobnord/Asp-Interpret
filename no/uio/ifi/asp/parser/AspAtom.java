package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public abstract class AspAtom extends AspSyntax {

    AspAtom(int n) {
        super(n);
    }

    static AspAtom parse(Scanner s) {
        enterParser("atom");
        AspAtom aa = null;
        switch (s.curToken().kind) {
            case falseToken:
                aa = AspBooleanLiteral.parse(s);
                break;
            case trueToken:
                aa = AspBooleanLiteral.parse(s);
                break;
            case floatToken:
                aa = AspFloatLiteral.parse(s);
                break;
            case integerToken:
                aa = AspIntegerLiteral.parse(s);
                break;
            case leftBraceToken:
                aa = AspDictDisplay.parse(s);
                break;
            case leftBracketToken:
                aa = AspListDisplay.parse(s);
                break;
            case leftParToken:
                aa = AspInnerExpr.parse(s);
                break;
            case nameToken:
                aa = AspName.parse(s);
                break;
            case noneToken:
                aa = AspNoneLiteral.parse(s);
                break;
            case stringToken:
                aa = AspStringLiteral.parse(s);
                break;
            default:
                parserError("Expected an expression atom but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        leaveParser("atom");
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
