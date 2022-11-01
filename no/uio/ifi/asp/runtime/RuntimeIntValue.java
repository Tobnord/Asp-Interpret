// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue {
    public long intValue;

    public RuntimeIntValue(long v) {
        intValue = v;
    }

    @Override
    String typeName() {
        return "integer";
    }

    @Override
    public String toString() {
        return (Long.toString(intValue));
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return intValue;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return (double)intValue;
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return (Long.toString(intValue));
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(this.intValue + v.getIntValue("long", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(this.intValue + v.getFloatValue("float", where));
        }

        runtimeError("Type error for +.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(this.intValue - v.getIntValue("long", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(this.intValue - v.getFloatValue("float", where));
        }

        runtimeError("Type error for -.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where){
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(this.getFloatValue("float", where) / v.getIntValue("long", where) );
        }

        runtimeError("Type error for /.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(this.intValue / v.getIntValue("long", where));
        }

        runtimeError("Type error for int/.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(this.intValue * v.getIntValue("long", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(this.intValue * v.getFloatValue("float", where));
        }

        runtimeError("Type error for *.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(this.intValue == v.getIntValue("long", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(this.intValue == v.getFloatValue("float", where));
        }

        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {

        if(v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(this.intValue != v.getIntValue("long", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(this.intValue != v.getFloatValue("float", where));
        }

        runtimeError("Type error for !=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeIntValue(0);
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(this.intValue > v.getIntValue("long", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(this.intValue > v.getFloatValue("float", where));
        }

        runtimeError("Type error for >.", where);
        return null; // Required by the compiler
    }   

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(this.intValue < v.getIntValue("long", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(this.intValue < v.getFloatValue("float", where));
        }

        runtimeError("Type error for <.", where);
        return null; // Required by the compiler
    }   

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(this.intValue >= v.getIntValue("long", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(this.intValue >= v.getFloatValue("float", where));
        }

        runtimeError("Type error for >=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(this.intValue <= v.getIntValue("long", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(this.intValue <= v.getFloatValue("float", where));
        }
        runtimeError("Type error for <=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(this.intValue % v.getIntValue("long", where));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(this.intValue % v.getFloatValue("float", where));
        }

        runtimeError("Type error for %.", where);
        return null; // Required by the compiler
    }  

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeIntValue(intValue *-1);
    }

    public RuntimeValue evalPositive(AspSyntax where) {

        if(intValue >= 0) {
            return new RuntimeIntValue(intValue);
        }
        else if(intValue < 0){
            return new RuntimeIntValue(intValue *-1);
        }
        
        runtimeError("Unary '+' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }
}
