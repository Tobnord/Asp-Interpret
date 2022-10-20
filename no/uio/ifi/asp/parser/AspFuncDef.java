package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspFuncDef extends AspCompoundStmt {
    AspName name;
    ArrayList<AspName> nameTests = new ArrayList<>();
    AspSuite suite;

    AspFuncDef(int n) {
        super(n);
    }

    static AspFuncDef parse(Scanner s) {
        enterParser("func def");
        AspFuncDef afd = new AspFuncDef(s.curLineNum());
        skip(s, TokenKind.defToken);
        afd.name = AspName.parse(s);
        skip(s, TokenKind.leftParToken);

        if (s.curToken().kind != TokenKind.rightParToken) {
            while(true) {
                afd.nameTests.add(AspName.parse(s));
                if (s.curToken().kind == TokenKind.rightParToken){
                    break;
                }
                skip(s, TokenKind.commaToken);
            }
        }

        skip(s, TokenKind.rightParToken);
        skip(s, TokenKind.colonToken);
        afd.suite = AspSuite.parse(s);
        leaveParser("func def");
        return afd;
    }

    @Override
    void prettyPrint(){
        prettyWrite("def ");
        this.name.prettyPrint();
        prettyWrite(" (");

        int counter = 0;
        if (nameTests.size() != 0){
            for (AspName name : nameTests) {
                name.prettyPrint();
                counter++;
                if(counter < nameTests.size()){
                    prettyWrite(", ");
                }
            }
        }

        prettyWrite(")");
        prettyWrite(":");
        this.suite.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
