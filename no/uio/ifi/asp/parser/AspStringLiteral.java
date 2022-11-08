package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
public class AspStringLiteral extends AspAtom {

    String stringLiteral;


    AspStringLiteral(int n) {
        super(n);
    }

    static AspStringLiteral parse(Scanner s) {
        enterParser("string literal");
        AspStringLiteral asl = new AspStringLiteral(s.curLineNum());
        asl.stringLiteral = s.curToken().stringLit.toString();
        skip(s, TokenKind.stringToken);
        leaveParser("string literal");
        return asl;
    }
    
    @Override
    void prettyPrint() {
        if (this.stringLiteral.contains("\"")) {
            prettyWrite("\'" + this.stringLiteral + "\'");
        }
        else if (this.stringLiteral.contains("\'")) {
            prettyWrite("\"" + this.stringLiteral + "\"");
        }
        else {
            prettyWrite("\"" + this.stringLiteral + "\"");
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //System.out.println("EVAL: String");
        return new RuntimeStringValue(this.stringLiteral);
    }
}
