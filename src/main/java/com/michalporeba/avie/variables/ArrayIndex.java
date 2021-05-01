package com.michalporeba.avie.variables;

public class ArrayIndex extends NumericVariable<Integer> {
    public ArrayIndex(ScalarVariable.Recorder recorder, String name) {
        super(recorder, name, 0);
    }

    public void increment() {
        add(1);
    }

    public void decrement() {
        add(-1);
    }

    public int next() { return get()+1; }
    public int previous() { return get()-1; }
}
