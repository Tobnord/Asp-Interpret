package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspFloatLiteral extends AspAtom {
    String floatLiteral = "";

    AspFloatLiteral(int n) {
        super(n);
    }

    static AspFloatLiteral parse(Scanner s) {
        enterParser("float literal");
        AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());
        afl.floatLiteral += s.curToken().floatLit;
        skip(s, TokenKind.floatToken);
        leaveParser("float literal");
        return afl;
    }

    @Override
    void prettyPrint(){
        prettyWrite(this.floatLiteral);
    }
    
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeFloatValue(Double.parseDouble(floatLiteral));
    }
}
