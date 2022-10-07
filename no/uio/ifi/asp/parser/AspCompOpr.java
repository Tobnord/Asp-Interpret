package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
public class AspCompOpr extends AspSyntax{

    TokenKind compOprToken;

    AspCompOpr(int n) {
        super(n);
    }


    static AspCompOpr parse(Scanner s) {
        AspCompOpr aco = new AspCompOpr(s.curLineNum());

        enterParser("comparison orerator");

        

        switch (s.curToken().kind) {

            case greaterToken:
                aco.compOprToken = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            case lessToken:
                aco.compOprToken = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            case greaterEqualToken:
                aco.compOprToken = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            case lessEqualToken:
                aco.compOprToken = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            case doubleEqualToken:
                aco.compOprToken = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            case notEqualToken:
                aco.compOprToken = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;
                
            default:
                parserError("Expected a compOpr but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }

        leaveParser("comaprison operator");
        return aco;
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
