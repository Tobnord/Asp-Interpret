package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

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
        // int nPrinted = 0;
        // for (AspTermOpr ato : termOprTests) {
        //     if (nPrinted > 0)
        //         prettyWrite(" and ");
        //     ato.prettyPrint();
        //     ++nPrinted;
        // }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
