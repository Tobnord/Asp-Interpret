package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspComparison extends AspSyntax {
    ArrayList<AspTerm> termTests = new ArrayList<>();
    ArrayList<AspCompOpr> CompOprTests = new ArrayList<>();
    
    AspComparison(int n) {
        super(n);
    }

    static AspComparison parse(Scanner s) {
        enterParser("comparison");

        AspComparison ac = new AspComparison(s.curLineNum());

        while (true) {
            ac.termTests.add(AspTerm.parse(s));
            if (s.isCompOpr() == false) {
                break;
            }
            ac.CompOprTests.add(AspCompOpr.parse(s));
        }

        leaveParser("comparison");
        return ac;
    }

    @Override
    void prettyPrint() {
        int nPrinted = 0;
        int counter = 0;
        for (AspTerm at : termTests) {
            if (nPrinted > 0)
                this.CompOprTests.get(counter-1).prettyPrint();
            at.prettyPrint();
            ++nPrinted;
            counter++;
        }
    }
    

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}