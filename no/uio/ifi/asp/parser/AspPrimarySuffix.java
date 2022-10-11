package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspPrimarySuffix extends AspSyntax {

    AspPrimarySuffix(int n) {
        super(n);
    }

    static AspPrimarySuffix parse(Scanner s) {
        enterParser("primary suffix");
        AspPrimarySuffix aps = null;

        if (s.curToken().kind == TokenKind.leftParToken) {
            aps = AspArguments.parse(s);

        }
        else if (s.curToken().kind == TokenKind.leftBracketToken) {
            aps = AspSubscriptions.parse(s);
        }
        else {
            parserError("Expected a primarySuffix but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        leaveParser("primary suffix");
        return aps;
    }

    @Override
    void prettyPrint(){
        
    }
}
