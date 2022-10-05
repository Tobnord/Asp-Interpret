package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspComparison extends AspSyntax {
    ArrayList<AspTerm> termTests = new ArrayList<>();

    AspComparison(int n) {
        super(n);
    }

    static AspComparison parse(Scanner s) {
        enterParser("comparison");
        AspComparison ac = new AspComparison(s.curLineNum());
        while (true) {
            ac.termTests.add(AspTerm.parse(s));
            // IDK WHAT TO ADD AS COMPARISON TOKEN
            // if (s.curToken().kind != TokenKind.WHATToken)
            //     break;
            // skip(s, TokenKind.WHATToken);
        }
        leaveParser("comparison");
        return ac;
    }

    @Override
    void prettyPrint() {
        int nPrinted = 0;
        for (AspTerm ant : termTests) {
            if (nPrinted > 0)
                prettyWrite(" and ");
            ant.prettyPrint();
            ++nPrinted;
        }
    }
    

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}