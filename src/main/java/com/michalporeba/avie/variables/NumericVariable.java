package com.michalporeba.avie.variables;

public class NumericVariable extends ScalarVariable {
    public NumericVariable(ScalarVariable.Recorder recorder, String name, int value) {
        super(recorder, name, value);
    }

    public void add(int value) {
        set(value + get());
    }
}
