package com.michalporeba.avie.variables;

public class NumericVariable<T extends Number> extends ScalarVariable<T> {
    public NumericVariable(ScalarVariable.Recorder recorder, String name, T value) {
        super(recorder, name, value);
    }

    public void add(T value) {
        if (value instanceof Integer) {
            set((T)Integer.valueOf(value.intValue()+get().intValue()));
        } else if (value instanceof Float) {
            set((T)Float.valueOf(value.floatValue()+get().floatValue()));
        }
    }
}
