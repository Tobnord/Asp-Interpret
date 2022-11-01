package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.main.*;

public class AspFactor extends AspSyntax {
    ArrayList<AspFactorOpr> factorOprTests = new ArrayList<>();
    ArrayList<AspPrimary> primaryTests = new ArrayList<>();
    Map<Integer, AspFactorPrefix> factorPrefixTests = new HashMap<Integer, AspFactorPrefix>();

    AspFactor(int n) {
        super(n);
    }

    public static AspFactor parse(Scanner s) {
        enterParser("factor");
        AspFactor af = new AspFactor(s.curLineNum());

        int counter = 0;
        while(true) {
            if (s.isFactorPrefix()) {
                af.factorPrefixTests.put(counter, AspFactorPrefix.parse(s));
            }
            af.primaryTests.add(AspPrimary.parse(s));
            counter++;
            
            if (s.isFactorOpr() == false) {
                break;
            }

            af.factorOprTests.add(AspFactorOpr.parse(s));
        }

        leaveParser("factor");
        return af;
    }

    @Override
    void prettyPrint() {
        int counter = 0;
        for (AspPrimary primary : primaryTests) {
            
            if (factorPrefixTests.get(counter) != null) {
                factorPrefixTests.get(counter).prettyPrint();
            }
            primary.prettyPrint();
            counter++;

            if (counter < this.primaryTests.size()) {
                factorOprTests.get(counter-1).prettyPrint();
            }
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        System.out.println("EVAL: factor");
        
        RuntimeValue v = primaryTests.get(0).eval(curScope);

        for (int i = 0; i < primaryTests.size(); ++i) {
            
            if (i > 0) {
                v = primaryTests.get(i).eval(curScope);
            }
            
            if (factorPrefixTests.get(i) != null) {
                TokenKind k = factorPrefixTests.get(i).kind;
                switch (k) {
                    case minusToken:
                        v = v.evalNegate(this); break;
                    case plusToken:
                        v = v.evalPositive(this); break;
                    default:
                        Main.panic("Illegal factor prefix operator: " + k + "!");
                }
            }

            if(factorOprTests.size() < i) {
                TokenKind f = factorOprTests.get(i).kind;
                switch (f) {
                    case astToken:
                        v = v.evalMultiply(factorOprTests.get(i).eval(curScope), this); break;
                    case slashToken:
                        v = v.evalDivide(factorOprTests.get(i).eval(curScope), this); break;
                    case percentToken:
                        v = v.evalModulo(factorOprTests.get(i).eval(curScope), this); break;
                    case doubleSlashToken:
                        v = v.evalIntDivide(factorOprTests.get(i).eval(curScope), this); break;
                    default:
                        Main.panic("Illegal factor operator: " + f + "!");
                }
            }
        }
        return v;
    }
}
