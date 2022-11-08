package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspComparison extends AspSyntax {
    ArrayList<AspTerm> termTests = new ArrayList<>();
    ArrayList<AspCompOpr> CompOprTests = new ArrayList<>();
    
    AspComparison(int n) {
        super(n);
    }

    static AspComparison parse(Scanner s) {
        enterParser("comparison");

        AspComparison ac = new AspComparison(s.curLineNum());

        while (true) {
            ac.termTests.add(AspTerm.parse(s));
            if (s.isCompOpr() == false) {
                break;
            }
            ac.CompOprTests.add(AspCompOpr.parse(s));
        }

        leaveParser("comparison");
        return ac;
    }

    @Override
    void prettyPrint() {
        int nPrinted = 0;
        int counter = 0;
        for (AspTerm at : termTests) {
            if (nPrinted > 0)
                this.CompOprTests.get(counter-1).prettyPrint();
            at.prettyPrint();
            ++nPrinted;
            counter++;
        }
    }
    

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //System.out.println("EVAL: comparison -->");
        RuntimeValue v = termTests.get(0).eval(curScope);
        RuntimeValue result = v;

        for (int i = 1; i < termTests.size(); ++i) {
            TokenKind k = CompOprTests.get(i-1).kind;

            RuntimeValue v2 = termTests.get(i).eval(curScope);
            
            switch (k) {
                case greaterToken:
                    result = v.evalGreater(termTests.get(i).eval(curScope), this); break;
                case lessToken:
                    result = v.evalLess(termTests.get(i).eval(curScope), this); break;
                case greaterEqualToken:
                    result = v.evalGreaterEqual(termTests.get(i).eval(curScope), this); break;
                case lessEqualToken:
                    result = v.evalLessEqual(termTests.get(i).eval(curScope), this); break;
                case doubleEqualToken:
                    result = v.evalEqual(termTests.get(i).eval(curScope), this);break;
                case notEqualToken:
                    result = v.evalNotEqual(termTests.get(i).eval(curScope), this);break;
                default:
                    result = v;
                    Main.panic("Illegal term operator: " + k + "!");
            }

            if (result.getBoolValue("comparison", this) == false) {
                return result;
            }
            v = v2;
        }
        
        return result;
    }

}