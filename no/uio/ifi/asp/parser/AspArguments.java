package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspArguments extends AspPrimarySuffix {

    AspArguments(int n) {
        super(n);
    }
    // ArrayList<AspArguments> ArgumentTests = new ArrayList<>();
    
    // AspArgument(int n){
    //     super(n);
    // }
    
    // static AspArguments parse(Scanner s) {
    //     enterParser("argument test");
    //     AspArgument arg = new AspArgument(s.curLineNum());
    //     while (true) {
    //         arg.ArgumentTests.add(AspArgument.parse(s));
    //         if (s.curToken().kind != TokenKind.ArgumentToken)
    //             break;
    //         skip(s, TokenKind.ArgumentToken);
    //     }
    //     leaveParser("argument test");
    //     return arg;
    // }

    @Override
    void prettyPrint(){
        
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
