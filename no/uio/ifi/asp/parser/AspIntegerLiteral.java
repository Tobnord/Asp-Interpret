package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspIntegerLiteral extends AspAtom{
    
    AspIntegerLiteral(int n) {
        super(n);
    }

    static AspIntegerLiteral parse(Scanner s) {
        enterParser("integer literal");
        AspIntegerLiteral ai = new AspIntegerLiteral(s.curLineNum());
        skip(s, TokenKind.integerToken);
        leaveParser("integer literal");
        return ai;
    }
    
    @Override
    void prettyPrint(){
        prettyWrite(" int ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
