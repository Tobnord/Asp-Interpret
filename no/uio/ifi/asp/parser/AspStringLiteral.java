package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspStringLiteral extends AspAtom {

    AspStringLiteral(int n) {
        super(n);
    }

    static AspStringLiteral parse(Scanner s) {
        enterParser("string literal");
        AspStringLiteral asl = new AspStringLiteral(s.curLineNum());
        skip(s, TokenKind.stringToken);
        leaveParser("string literal");
        return asl;
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
