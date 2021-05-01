package com.michalporeba.avie.variables;

public class ArrayIndex extends NumericVariable<Integer> {
    public ArrayIndex(ScalarVariable.Recorder recorder, String name) {
        this(recorder, name, 0);
    }

    private ArrayIndex(ScalarVariable.Recorder recorder, String name, Integer value) {
        super(recorder, name, value);
    }

    public void increment() {
        add(1);
    }

    public void decrement() {
        add(-1);
    }

    public ArrayIndex next() { return cloneWithValue(get()+1); }
    public ArrayIndex previous() { return cloneWithValue(get()-1); }

    private ArrayIndex cloneWithValue(Integer value) {
        return new ArrayIndex(getRecorder(), getName(), value);
    }
}
