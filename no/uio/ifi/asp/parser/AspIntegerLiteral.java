package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspIntegerLiteral extends AspAtom{
    long integerLiteral;
    
    AspIntegerLiteral(int n) {
        super(n);
    }

    @Override
    public String toString() {
        return "" + integerLiteral;
    }

    static AspIntegerLiteral parse(Scanner s) {
        enterParser("integer literal");
        AspIntegerLiteral ai = new AspIntegerLiteral(s.curLineNum());
        ai.integerLiteral = s.curToken().integerLit;
        skip(s, TokenKind.integerToken);
        leaveParser("integer literal");
        return ai;
    }
    
    @Override
    void prettyPrint(){
        prettyWrite(Long.toString(this.integerLiteral));
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //System.out.println("EVAL: Integer");
        return new RuntimeIntValue(integerLiteral);
    }
}
