package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspTerm extends AspSyntax{
    ArrayList<AspTermOpr> termOprTests = new ArrayList<>();
    ArrayList<AspFactor> factorTests = new ArrayList<>();

    AspTerm(int n) {
        super(n);
    }

    static AspTerm parse(Scanner s) {
        enterParser("term");

        AspTerm at = new AspTerm(s.curLineNum());

        while (true) {
            at.factorTests.add(AspFactor.parse(s));
            if (s.curToken().kind != TokenKind.plusToken && s.curToken().kind != TokenKind.minusToken) {
                break;
            }
            at.termOprTests.add(AspTermOpr.parse(s));
        }

        leaveParser("term");
        return at;
    }

    @Override
    public void prettyPrint() {
        int counter = 0;
        for (AspFactor factor : factorTests) {
            factor.prettyPrint();
            
            if (counter < this.termOprTests.size()) {
               
                this.termOprTests.get(counter).prettyPrint();
            }
            counter++;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
