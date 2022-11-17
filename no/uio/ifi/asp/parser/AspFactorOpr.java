package no.uio.ifi.asp.parser;


import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspFactorOpr extends AspSyntax{
    
    TokenKind kind;

    AspFactorOpr(int n) {
        super(n);
    }

    @Override
    public String toString() {
        String returnString = "";
        returnString += (this.kind.toString());
        return returnString;
    }
    

    static AspFactorOpr parse(Scanner s) {
        AspFactorOpr afo = new AspFactorOpr(s.curLineNum());

        enterParser("factor operator");

        switch (s.curToken().kind) {

            case astToken:
                afo.kind = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            case slashToken:
                afo.kind = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            case percentToken:
                afo.kind = s.curToken().kind;
                skip(s, s.curToken().kind);
                break;

            case doubleSlashToken:
                afo.kind = s.curToken().kind;
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
        prettyWrite(" " + this.kind.toString() + " ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
