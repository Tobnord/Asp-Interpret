package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspNotTest extends AspSyntax {

    boolean withNot = false;
    AspComparison comparison;

    AspNotTest(int n) {
        super(n);
    }

    @Override
    public String toString() {
        String returnString = "";

        if (withNot) {
            returnString += "not";
        }
        returnString += comparison.toString();

        return returnString;
    }

    static AspNotTest parse(Scanner s) {
        enterParser("not test");
        AspNotTest ant = new AspNotTest(s.curLineNum());

        if (s.curToken().kind == TokenKind.notToken) {
            ant.withNot = true;
            skip(s, TokenKind.notToken);
        }

        ant.comparison = AspComparison.parse(s);

        leaveParser("not test");
        return ant;
    }

    @Override
    void prettyPrint() {
        if (withNot){
            prettyWrite(" not ");
        }
        this.comparison.prettyPrint();
    }
    

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //System.out.println("EVAL: AspNot");
        
        RuntimeValue v = comparison.eval(curScope);
        if (withNot) {
            v = v.evalNot(this);
        }
        return v;
    }
    
}
