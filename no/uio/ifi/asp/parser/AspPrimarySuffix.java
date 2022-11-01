package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.*;

public abstract class AspPrimarySuffix extends AspSyntax {
    static ArrayList<AspPrimarySuffix> primarySuffixList = new ArrayList<>();
    

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
        primarySuffixList.add(aps); 
        leaveParser("primary suffix");
        return aps;
    }

    @Override
    void prettyPrint() {
        AspPrimarySuffix aa = primarySuffixList.get(0);
        aa.prettyPrint();
        primarySuffixList.remove(0);
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        System.out.println("EVAL: Primary Suffix");
        
        return primarySuffixList.get(0).eval(curScope);
    }
}
