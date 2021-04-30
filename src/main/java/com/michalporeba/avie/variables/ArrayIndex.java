package com.michalporeba.avie.variables;

public class ArrayIndex extends NumericVariable<Integer> {
    public ArrayIndex(AccessRecorder recorder, String name) {
        super(recorder, name, 0);
    }

    public void increment() {
        super.add(1);
    }

    public void decrement() {
        super.add(-1);
    }
}
