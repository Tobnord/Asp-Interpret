package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {

    double floatValue;

    public RuntimeFloatValue(double v) {
        floatValue = v;
    }

    @Override
    String typeName() {
        return "float";
    }
    
    @Override
    public String toString() {
        return Double.toString(floatValue);
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if(floatValue == 0){
            return false;
        }
        return true;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return floatValue;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(this.getFloatValue("float", where) + v.getFloatValue("float", where));
        }

        runtimeError("Type error for +.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(this.getFloatValue("float", where) - v.getFloatValue("float", where));
        }

        runtimeError("Type error for -.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where){
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(this.getFloatValue("float", where) / v.getFloatValue("float", where));
        }

        runtimeError("Type error for /.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where){
        if(v.getIntValue("long", where) == 0 || v.getIntValue("long", where) == -0) {
            return new RuntimeIntValue(0);
        }
        if(v instanceof RuntimeIntValue) {
            return new RuntimeFloatValue(Math.floor(this.floatValue / v.getIntValue("long", where)));
        }
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(Math.floor(this.floatValue / v.getFloatValue("long", where)));
        }

        runtimeError("Type error for int/.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(this.floatValue * v.getFloatValue("float", where));
        }

        runtimeError("Type error for *.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(this.floatValue == v.getFloatValue("float", where));
        }

        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {

        if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(this.floatValue != v.getFloatValue("float", where));
        }

        runtimeError("Type error for !=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if (this.floatValue == 0) {
            return new RuntimeBoolValue(true);
        }
        else {
            return new RuntimeBoolValue(false);
        }
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(this.floatValue > v.getFloatValue("float", where));
        }

        runtimeError("Type error for >.", where);
        return null; // Required by the compiler
    }   

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(this.floatValue < v.getFloatValue("float", where));
        }

        runtimeError("Type error for <.", where);
        return null; // Required by the compiler
    }   

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(this.floatValue > v.getFloatValue("float", where));
        }

        runtimeError("Type error for >=.", where);
        return null; // Required by the compiler
    }   

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(this.floatValue <= v.getFloatValue("float", where));
        }


        runtimeError("Type error for <=.", where);
        return null; // Required by the compiler
    }   

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(this.floatValue - v.getFloatValue("float", where) * Math.floor(this.floatValue / v.getFloatValue("float", where)));
        }

        runtimeError("Type error for %.", where);
        return null; // Required by the compiler
    }  

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeFloatValue(floatValue * - 1);
    }

    public RuntimeValue evalPositive(AspSyntax where) {

        if(floatValue >= 0) {
            return new RuntimeFloatValue(floatValue);
        }
        else if(floatValue < 0){
            return new RuntimeFloatValue(floatValue *-1);
        }
        
        runtimeError("Unary '+' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler!
    }
}
