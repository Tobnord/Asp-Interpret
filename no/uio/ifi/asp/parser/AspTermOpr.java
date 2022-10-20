package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspTermOpr extends AspSyntax {
    boolean isPlusToken = false;

    AspTermOpr(int n) {
        super(n);
    }

    static AspTermOpr parse(Scanner s) {
        enterParser("term opr");
        AspTermOpr ato = new AspTermOpr(s.curLineNum());
        if (s.curToken().kind == plusToken) {
            skip(s, TokenKind.plusToken);
            ato.isPlusToken = true;
        }
        else if (s.curToken().kind == minusToken) {
            skip(s, TokenKind.minusToken);
            ato.isPlusToken = false;
        }
        leaveParser("term opr");
        return ato;
    }

    @Override
    void prettyPrint() {
        if(isPlusToken) {
            prettyWrite(" + ");
        }
        else {
            prettyWrite(" - ");
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
