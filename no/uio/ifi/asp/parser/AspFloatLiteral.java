package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspFloatLiteral extends AspAtom {


    AspFloatLiteral(int n) {
        super(n);
    }

    static AspFloatLiteral parse(Scanner s) {
        enterParser("float literal");
        AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());
        skip(s, TokenKind.floatToken);
        leaveParser("float literal");
        return afl;
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
