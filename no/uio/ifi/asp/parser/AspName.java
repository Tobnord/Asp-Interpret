package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.*;
public class AspName extends AspAtom {
    String nameString;

    AspName(int n) {
        super(n);
    }

    static AspName parse(Scanner s) {
        enterParser("name");
        AspName an = new AspName(s.curLineNum());
        an.nameString = s.curToken().name.toString();
        skip(s, TokenKind.nameToken);
        leaveParser("name");
        return an;
    }

    @Override
    void prettyPrint() {
        prettyWrite(this.nameString);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        System.out.println("EVAL: Name");
        return null;
    }

}


