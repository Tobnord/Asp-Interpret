package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspNotTest extends AspSyntax {

    boolean notToken = false;
    AspComparison comparison;

    AspNotTest(int n) {
        super(n);
    }

    static AspNotTest parse(Scanner s) {
        enterParser("not test");
        AspNotTest ant = new AspNotTest(s.curLineNum());

        if (s.curToken().kind == TokenKind.notToken) {
            ant.notToken = true;
            skip(s, TokenKind.notToken);
        }

        ant.comparison = AspComparison.parse(s);

        leaveParser("not test");
        return ant;
    }

    @Override
    void prettyPrint() {
        if (notToken){
            prettyWrite(" not ");
        }
        this.comparison.prettyPrint();
    }
    

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
