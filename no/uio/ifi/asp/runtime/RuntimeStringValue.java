// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
    String stringValue;

    public RuntimeStringValue(String v) {
        stringValue = v;
    }

    @Override
    String typeName() {
        return "string";
    }

    @Override
    public String toString() {
        return stringValue;
    }

    @Override
    public String showInfo() {
        if (stringValue.indexOf('\'') >= 0)
            return '"' + stringValue + '"';
        else
            return "'" + stringValue + "'";
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return Long.parseLong(stringValue);
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return Double.parseDouble(stringValue);
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return stringValue;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return Boolean.parseBoolean(stringValue);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeStringValue("");
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
        if(isRuntimeInstance(v)) {
            return new RuntimeStringValue(this.stringValue + v.getStringValue("string", where));
        }
        runtimeError("Type error for +.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if(isRuntimeInstance(v)) {
            return new RuntimeBoolValue(this.stringValue == v.getStringValue("string", where));
        }
        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {    
        if(isRuntimeInstance(v)) {
            return new RuntimeBoolValue(this.stringValue != v.getStringValue("string", where));
        }
        
        runtimeError("Type error for !=.", where);
        return null; // Required by the compiler
    }

    Boolean isRuntimeInstance(RuntimeValue value) {
        Boolean bool = false;

        if (value instanceof RuntimeIntValue) {
            bool = true;
        }
        else if (value instanceof RuntimeFloatValue) {
            bool = true;
        }
        else if (value instanceof RuntimeBoolValue) {
            bool = true;
        }
        else if (value instanceof RuntimeDictionaryValue) {
            bool = true;
        }
        else if (value instanceof RuntimeListValue) {
            bool = true;
        }
        else if (value instanceof RuntimeStringValue) {
            bool = true;
        }

        return bool;
    }
}

