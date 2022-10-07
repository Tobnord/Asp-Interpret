package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFuncDef extends AspCompoundStmt {
    AspName name;
    ArrayList<AspName> nameTests = new ArrayList<>();
    AspSuite suite;

    AspFuncDef(int n) {
        super(n);
    }

    static AspFuncDef parse(Scanner s) {
        AspFuncDef afd = new AspFuncDef(s.curLineNum());
        skip(s, TokenKind.defToken);
        afd.name = AspName.parse(s);
        skip(s, TokenKind.leftParToken);

        while(true) {
            afd.nameTests.add(AspName.parse(s));
            if (s.curToken().kind == TokenKind.rightParToken){
                break;
            }
            skip(s, TokenKind.commaToken);
        }

        skip(s, TokenKind.rightParToken);
        skip(s, TokenKind.colonToken);
        afd.suite = AspSuite.parse(s);
        return afd;
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
