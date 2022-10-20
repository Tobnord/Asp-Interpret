package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;

public class AspName extends AspAtom{
    AspName name;

    AspName(int n) {
        super(n);
    }

    static AspName parse(Scanner s) {
        enterParser("name");
        AspName an = new AspName(s.curLineNum());
        skip(s, TokenKind.nameToken);
        leaveParser("name");
        return an;
    }

    @Override
    void prettyPrint(){
            prettyWrite(" name ");
    }

}


