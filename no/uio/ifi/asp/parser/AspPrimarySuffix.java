package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;

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
