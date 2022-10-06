package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspTerm extends AspSyntax{
    ArrayList<no.uio.ifi.asp.parser.AspTermOprTest> termOprTests = new ArrayList<>();

    AspTerm(int n) {
        super(n);
    }

    static AspTerm parse(Scanner s) {
        enterParser("term test");
        AspTerm at= new AspTerm(s.curLineNum());
        while (true) {
            at.termOprTests.add(AspTerm.parse(s));
            //IDK WHAT TOKENS TO MAKE HERE
            // if (s.curToken().kind != TokenKind.WHATToken)
            //     break;
            // skip(s, TokenKind.WHATToken);
        }
        leaveParser("term test");
        return at;
    }

    @Override
    public void prettyPrint() {
        int nPrinted = 0;
        for (AspTerm at : termOprTests) {
            if (nPrinted > 0)
                prettyWrite(" and ");
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
