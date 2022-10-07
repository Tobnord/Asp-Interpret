package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactorOpr extends AspSyntax{
    TokenKind factorOprToken;


    AspFactorOpr(int n) {
        super(n);
    }

    static AspFactorOpr parse(Scanner s) {
        AspFactorOpr afo = new AspFactorOpr(s.curLineNum());

        enterParser("factor operator");

        switch (s.curToken().kind) {

            case astToken:
                afo.factorOprToken = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            case slashToken:
                afo.factorOprToken = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            case percentToken:
                afo.factorOprToken = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            case doubleSlashToken:
                afo.factorOprToken = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            default:
                parserError("Expected a factorOpr but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }

        leaveParser("factor operator");
        return afo;
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
