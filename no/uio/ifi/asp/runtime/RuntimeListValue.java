package no.uio.ifi.asp.runtime;
import java.util.List;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeListValue extends RuntimeValue {
    List listValue;

    public RuntimeListValue(List v) {
        listValue = v;
    }

    @Override
    String typeName() {
        return "list";
    }
    
    @Override
    public String toString() {
        return listValue.toString();
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeListValue(null);
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeNoneValue) {
            return v;
        }

        runtimeError("Type error for +.", where);
        return null;
    }
    
}
