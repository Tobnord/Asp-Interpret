package no.uio.ifi.asp.parser;


import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
public class AspCompOpr extends AspSyntax{

    TokenKind kind;

    AspCompOpr(int n) {
        super(n);
    }


    static AspCompOpr parse(Scanner s) {
        AspCompOpr aco = new AspCompOpr(s.curLineNum());

        enterParser("comparison orerator");

        switch (s.curToken().kind) {

            case greaterToken:
                aco.kind = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            case lessToken:
                aco.kind = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            case greaterEqualToken:
                aco.kind = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            case lessEqualToken:
                aco.kind = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            case doubleEqualToken:
                aco.kind = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            case notEqualToken:
                aco.kind = s.curToken().kind;
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
        prettyWrite(" " + this.kind.toString() + " ");
    }
    
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
