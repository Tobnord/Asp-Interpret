package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.scanner.*;
public abstract class AspCompoundStmt extends AspStmt {

    static ArrayList<AspCompoundStmt> acsArrayList = new ArrayList<>();
    
    AspCompoundStmt(int n) {
        super(n);
    }

    static AspCompoundStmt parse(Scanner s) {
        enterParser("compound stmt");
        AspCompoundStmt acs = null;
        switch (s.curToken().kind) {
            case forToken:
                acs = AspForStmt.parse(s);
                break;
            case ifToken:
                acs = AspIfStmt.parse(s);
                break;
            case whileToken:
                acs = AspWhileStmt.parse(s);
                break;
            case defToken:
                acs = AspFuncDef.parse(s);
                break;
            default:
                parserError("Expected a compound stmt but found a " +
                        s.curToken().kind + "!", s.curLineNum());
        }
        acsArrayList.add(acs);
        leaveParser("compound stmt");
        return acs;
    }

    @Override
    void prettyPrint() {
        AspCompoundStmt acs = acsArrayList.get(0);
        acs.prettyPrint();
        acsArrayList.remove(0);
    }
}
