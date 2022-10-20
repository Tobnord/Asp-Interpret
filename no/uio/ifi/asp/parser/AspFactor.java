package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

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
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
