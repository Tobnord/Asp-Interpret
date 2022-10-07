package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactor extends AspSyntax {
    ArrayList<AspFactorPrefix> factorPrefixTests = new ArrayList<>();
    ArrayList<AspFactorOpr> factorOprTests = new ArrayList<>();
    ArrayList<AspPrimary> primaryTests = new ArrayList<>();

    AspFactor(int n) {
        super(n);
    }

    public static AspFactor parse(Scanner s) {
        enterParser("factor");
        AspFactor af = new AspFactor(s.curLineNum());


        while(true) {
            if (s.curToken().kind == TokenKind.plusToken || s.curToken().kind == TokenKind.minusToken) {
                af.factorPrefixTests.add(AspFactorPrefix.parse(s));
            }

            af.primaryTests.add(AspPrimary.parse(s));

            if (s.curToken().kind != TokenKind.plusToken && s.curToken().kind != TokenKind.minusToken) {
                break;
            }

            af.factorOprTests.add(AspFactorOpr.parse(s));
        }

        skip(s, TokenKind.rightBracketToken);

        leaveParser("factor");
        return af;
    }

    @Override
    void prettyPrint(){
        
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
