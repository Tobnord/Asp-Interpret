package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspGlobalStmt extends AspSmallStmt {
    ArrayList<AspName> nameTests = new ArrayList<>();

    AspGlobalStmt(int n) {
        super(n);
    }

    static AspGlobalStmt parse(Scanner s) {
        enterParser("global statement");
        AspGlobalStmt ags = new AspGlobalStmt(s.curLineNum());

        skip(s, TokenKind.globalToken);

        while(true) {
            ags.nameTests.add(AspName.parse(s));
            if (s.curToken().kind != TokenKind.commaToken) {
                break;
            }
            skip(s, TokenKind.commaToken);
        }

        leaveParser("global statement");
        return ags;
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
