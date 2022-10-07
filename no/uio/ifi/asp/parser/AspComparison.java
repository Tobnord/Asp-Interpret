package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspComparison extends AspSyntax {
    ArrayList<AspTerm> termTests = new ArrayList<>();
    ArrayList<AspCompOpr> CompOprTests = new ArrayList<>();
    
    static List<TokenKind> compOprTokenlist = new ArrayList<>(
        Arrays.asList(
            TokenKind.greaterEqualToken,
            TokenKind.lessEqualToken,
            TokenKind.doubleEqualToken,
            TokenKind.notEqualToken,
            TokenKind.lessToken,
            TokenKind.greaterToken
        )
    );

    AspComparison(int n) {
        super(n);
    }

    static AspComparison parse(Scanner s) {
        enterParser("comparison");

        AspComparison ac = new AspComparison(s.curLineNum());

        while (true) {
            ac.termTests.add(AspTerm.parse(s));
            if (compOprTokenlist.contains(s.curToken().kind)) {
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
        for (AspTerm at : termTests) {
            if (nPrinted > 0)
                prettyWrite(" comparison ");
            at.prettyPrint();
            ++nPrinted;
        }
    }
    

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}