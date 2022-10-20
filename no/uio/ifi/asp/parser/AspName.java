package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;

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

}


