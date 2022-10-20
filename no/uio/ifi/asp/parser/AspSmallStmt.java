package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

abstract class AspSmallStmt extends AspSyntax {
    static ArrayList<AspSmallStmt> asArrayList = new ArrayList<>();
    
    AspSmallStmt(int n) {
        super(n);
    }

    static AspSmallStmt parse(Scanner s) {
        enterParser("small stmt");
        AspSmallStmt as = null;
        TokenKind cur = s.curToken().kind;
        if (cur == globalToken)
            as = AspGlobalStmt.parse(s);
        else if (cur == passToken)
            as = AspPassStmt.parse(s);
        else if (cur == returnToken)
            as = AspReturnStmt.parse(s);
        else if (cur == nameToken && s.anyEqualToken()){
            as = AspAssignment.parse(s);}
        else
            as = AspExprStmt.parse(s);
        asArrayList.add(as);
        leaveParser("small stmt");
        return as;
    }
    
    @Override
    void prettyPrint() {
        AspSmallStmt as = asArrayList.get(0);
        as.prettyPrint();
        asArrayList.remove(0);
    }
}