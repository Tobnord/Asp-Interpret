abstract class AspSmallStmt extends AspSyntax {
    AspSmallStmt(int n) {
        super(n);
    }

    static AspSmallStmt parse(Scanner s) {
        AspSmallStmt as = null;
        TokenKind cur = s.curToken().kind;
        if (cur == globalToken)
            as = AspGlobalStmt.parse(s);
        else if (cur == passToken)
            as = AspPassStmt.parse(s);
        else if (cur == returnToken)
            as = AspReturnStmt.parse(s);
        else if (cur == nameToken && s.anyEqualToken())
            as = AspAssignment.parse(s);
        else
            as = AspExprStmt.parse(s);
        return as;
    }
}