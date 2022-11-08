package no.uio.ifi.asp.runtime;
import java.util.ArrayList;
import java.util.List;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.*;

public class RuntimeListValue extends RuntimeValue {
    List<RuntimeValue> listValue;

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
        return new RuntimeListValue(new ArrayList<RuntimeValue>());
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeNoneValue){
            return new RuntimeBoolValue(false);
        }

        if (v instanceof RuntimeListValue){
            RuntimeListValue vListValue = (RuntimeListValue)v;
            if (this.listValue.equals(vListValue.listValue)){
                return new RuntimeBoolValue(true);
            }
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeNoneValue){
            return new RuntimeBoolValue(true);
        }

        if (v instanceof RuntimeListValue){
            RuntimeListValue vListValue = (RuntimeListValue)v;
            if (!this.listValue.equals(vListValue.listValue)){
                return new RuntimeBoolValue(true);
            }
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error for !=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
        List<RuntimeValue> tmpList = new ArrayList<>();

        for (int i = 0; i < this.listValue.size(); i++) {
            if (this.listValue.get(i) instanceof RuntimeIntValue) {

                RuntimeIntValue runtime = (RuntimeIntValue) this.listValue.get(i);
                if (v instanceof RuntimeIntValue) {
                    tmpList.add(i, runtime.evalAdd(v, where));                }
                else if (v instanceof RuntimeFloatValue) {
                    tmpList.add(i, runtime.evalAdd(v, where));
                }
            }
            else if (this.listValue.get(i) instanceof RuntimeFloatValue) {
                RuntimeFloatValue runtime = (RuntimeFloatValue) this.listValue.get(i);
                tmpList.add(i, runtime.evalAdd(v, where));
            }
            else if (this.listValue.get(i) instanceof RuntimeStringValue) {
                RuntimeStringValue runtime = (RuntimeStringValue) this.listValue.get(i);
                tmpList.add(i, runtime.evalAdd(v, where));
            }
        }

        if (!tmpList.isEmpty()) {
            return new RuntimeListValue(tmpList);
        }

        runtimeError("Type error for +.", where);
        return null;
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        List<RuntimeValue> tmpList = new ArrayList<>();

        for (int i = 0; i < this.listValue.size(); i++) {
            if (this.listValue.get(i) instanceof RuntimeIntValue) {

                RuntimeIntValue runtime = (RuntimeIntValue) this.listValue.get(i);
                if (v instanceof RuntimeIntValue) {
                    tmpList.add(i, runtime.evalSubtract(v, where));                }
                else if (v instanceof RuntimeFloatValue) {
                    tmpList.add(i, runtime.evalSubtract(v, where));
                }
            }
            else if (this.listValue.get(i) instanceof RuntimeFloatValue) {
                RuntimeFloatValue runtime = (RuntimeFloatValue) this.listValue.get(i);
                tmpList.add(i, runtime.evalSubtract(v, where));
            }
            else if (this.listValue.get(i) instanceof RuntimeStringValue) {
                RuntimeStringValue runtime = (RuntimeStringValue) this.listValue.get(i);
                tmpList.add(i, runtime.evalSubtract(v, where));
            }
        }

        if (!tmpList.isEmpty()) {
            return new RuntimeListValue(tmpList);
        }

        runtimeError("Type error for +.", where);
        return null;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        List<RuntimeValue> tmpList = new ArrayList<>();

        for (int i = 0; i < this.listValue.size(); i++) {
            if (this.listValue.get(i) instanceof RuntimeIntValue) {

                RuntimeIntValue runtime = (RuntimeIntValue) this.listValue.get(i);
                if (v instanceof RuntimeIntValue) {
                    tmpList.add(runtime.evalMultiply(v, where));
                }
                else if (v instanceof RuntimeFloatValue) {
                    tmpList.add(runtime.evalMultiply(v, where));
                }
            }
            else if (this.listValue.get(i) instanceof RuntimeFloatValue) {
                RuntimeFloatValue runtime = (RuntimeFloatValue) this.listValue.get(i);
                tmpList.add(i, runtime.evalMultiply(v, where));
            }
            else if (this.listValue.get(i) instanceof RuntimeStringValue){
                RuntimeStringValue runtime = (RuntimeStringValue) this.listValue.get(i);
                tmpList.add(i, runtime.evalMultiply(v, where));
            }
        }

        if (!tmpList.isEmpty()) {
            return new RuntimeListValue(tmpList);
        }
     
        runtimeError("Type error for *.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        List<RuntimeValue> tmpList = new ArrayList<>();

        for (int i = 0; i < this.listValue.size(); i++) {
            if (this.listValue.get(i) instanceof RuntimeIntValue) {

                RuntimeIntValue runtime = (RuntimeIntValue) this.listValue.get(i);
                if (v instanceof RuntimeIntValue) {
                    tmpList.add(runtime.evalDivide(v, where));
                }
                else if (v instanceof RuntimeFloatValue) {
                    tmpList.add(runtime.evalDivide(v, where));
                }
            }
            else if (this.listValue.get(i) instanceof RuntimeFloatValue) {
                RuntimeFloatValue runtime = (RuntimeFloatValue) this.listValue.get(i);
                tmpList.add(i, runtime.evalDivide(v, where));
            }
            else if (this.listValue.get(i) instanceof RuntimeStringValue){
                RuntimeStringValue runtime = (RuntimeStringValue) this.listValue.get(i);
                tmpList.add(i, runtime.evalDivide(v, where));
            }
        }

        if (!tmpList.isEmpty()) {
            return new RuntimeListValue(tmpList);
        }

        runtimeError("Type error for /.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {

        List<RuntimeValue> tmpList = new ArrayList<>();

        for (int i = 0; i < this.listValue.size(); i++) {
            if (this.listValue.get(i) instanceof RuntimeIntValue) {

                RuntimeIntValue runtime = (RuntimeIntValue) this.listValue.get(i);
                if (v instanceof RuntimeIntValue) {
                    tmpList.add(runtime.evalIntDivide(v, where));
                }
                else if (v instanceof RuntimeFloatValue) {
                    tmpList.add(runtime.evalIntDivide(v, where));
                }
            }
            else if (this.listValue.get(i) instanceof RuntimeFloatValue) {
                RuntimeFloatValue runtime = (RuntimeFloatValue) this.listValue.get(i);
                tmpList.add(i, runtime.evalIntDivide(v, where));
            }
            else if (this.listValue.get(i) instanceof RuntimeStringValue){
                RuntimeStringValue runtime = (RuntimeStringValue) this.listValue.get(i);
                tmpList.add(i, runtime.evalIntDivide(v, where));
            }
        }

        if (!tmpList.isEmpty()) {
            return new RuntimeListValue(tmpList);
        }

        runtimeError("Type error for /.", where);
        return null; // Required by the compiler
    }
    
}
