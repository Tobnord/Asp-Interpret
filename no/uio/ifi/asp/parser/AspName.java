package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspName extends AspAtom{
    AspName name;

    AspName(int n) {
        super(n);
    }

    static AspName parse(Scanner s) {
        AspName an = new AspName(s.curLineNum());
        skip(s, TokenKind.nameToken);
        an.name = AspName.parse(s);
        return an;
    }

    @Override
    void prettyPrint(){

    }

}
