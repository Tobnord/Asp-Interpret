package no.uio.ifi.asp.parser;

import java.util.HashMap;
import java.util.Map;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
public class AspDictDisplay extends AspAtom {

    Map<AspStringLiteral, AspExpr> dictDisplayHashMap = new HashMap<AspStringLiteral, AspExpr>();
        

    AspDictDisplay(int n) {
        super(n);
    }

    static AspDictDisplay parse(Scanner s) {
        enterParser("dict display");
        AspDictDisplay add = new AspDictDisplay(s.curLineNum());
        
        skip(s, TokenKind.leftBraceToken);

        if (s.curToken().kind != TokenKind.rightBracketToken) {

            while(true) {
                if (s.curToken().kind == TokenKind.commaToken) {
                    skip(s, commaToken);
                }

                AspStringLiteral stringLiteral = AspStringLiteral.parse(s);
                skip(s, TokenKind.colonToken);
                AspExpr expression = AspExpr.parse(s);

                add.dictDisplayHashMap.put(stringLiteral, expression);

                if (s.curToken().kind == TokenKind.rightBraceToken) {
                    break;
                }
            }
        }

        skip(s, TokenKind.rightBraceToken);

        leaveParser("dict display");
        return add;
    }

    @Override
    void prettyPrint(){
        prettyWrite("{");
        int nPrinted = 0;
        for (Map.Entry<AspStringLiteral, AspExpr> entry : dictDisplayHashMap.entrySet()) {
            entry.getKey().prettyPrint();
            prettyWrite(" : ");
            entry.getValue().prettyPrint();
            ++nPrinted;

            if (nPrinted < dictDisplayHashMap.size()) {
                prettyWrite(", ");
            }
        }
        prettyWrite("}");
    }
    
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
