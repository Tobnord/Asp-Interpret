package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspDictDisplay;
import no.uio.ifi.asp.parser.AspSyntax;

import java.util.HashMap;

import no.uio.ifi.asp.parser.*;


public class RuntimeDictionaryValue extends RuntimeValue {
    AspDictDisplay dictValue;

    public RuntimeDictionaryValue(AspDictDisplay v) {
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
        if (dictValue.dictDisplayHashMap.isEmpty()) {
            return false;
        }
        else{
            return true;
        }
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
        if (this.dictValue.dictDisplayHashMap.isEmpty()) {
            return new RuntimeBoolValue(true);
        }
        else {
            return new RuntimeBoolValue(false);
        }
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
