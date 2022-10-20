package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspNoneLiteral extends AspAtom {

    AspNoneLiteral(int n) {
        super(n);
    }

    static AspNoneLiteral parse(Scanner s) {
        enterParser("none literal");
        AspNoneLiteral anl = new AspNoneLiteral(s.curLineNum());
        skip(s, TokenKind.noneToken);
        leaveParser("none literal");
        return anl;
    }
    
    @Override
    void prettyPrint(){
        prettyWrite(" none ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
