class AspWhileStmt extends AspCompoundStmt {
    AspExpr test;
    AspSuite body;
    AspWhileStmt(int n) {
    super(n);
    }
    static AspWhileStmt parse(Scanner s) {
    AspWhileStmt aws = new AspWhileStmt(s.curLineNum());
    skip(s, whileToken); aws.test = AspExpr.parse(s);
    skip(s, colonToken); aws.body = AspSuite.parse(s);
    return aws;
    }
}
