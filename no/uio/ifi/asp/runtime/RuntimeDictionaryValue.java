package no.uio.ifi.asp.runtime;

import java.util.Collections;
import java.util.Map;

import no.uio.ifi.asp.parser.AspSyntax;


public class RuntimeDictionaryValue extends RuntimeValue{
    Map dictValue;

    public RuntimeDictionaryValue(Map v) {
        dictValue = v;
    }

    @Override
    String typeName() {
        return "dictionary";
    }

    @Override
    public String toString() {
        return dictValue.toString();
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        runtimeError("Type error: " + what + " is not a Boolean!", where);
        return false; // Required by the compiler!
    }
    

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }
        if (v instanceof RuntimeDictionaryValue){
            RuntimeDictionaryValue vDictValue = (RuntimeDictionaryValue)v;
            if (this.dictValue.equals(vDictValue.dictValue)){
                return new RuntimeBoolValue(true);
            }
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler
    }


    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }
        if (v instanceof RuntimeDictionaryValue){
            RuntimeDictionaryValue vDictValue = (RuntimeDictionaryValue)v;
            if (!this.dictValue.equals(vDictValue.dictValue)){
                return new RuntimeBoolValue(true);
            }
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler
    }

    
    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeDictionaryValue(Collections.emptyMap());
    }
    
    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error for +.", where);
        return null;
    }
}
