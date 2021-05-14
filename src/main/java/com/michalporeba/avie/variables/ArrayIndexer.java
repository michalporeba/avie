package com.michalporeba.avie.variables;

public class ArrayIndexer extends NumericVariable {
    public ArrayIndexer(ScalarVariable.Recorder recorder, String name) {
        this(recorder, name, 0);
    }

    private ArrayIndexer(ScalarVariable.Recorder recorder, String name, Integer value) {
        super(recorder, name, value);
    }

    public void increment() {
        add(1);
    }

    public void decrement() {
        add(-1);
    }

    public void set(ArrayIndexer indexer) {
        set(indexer.get());
    }

    public ArrayIndexer next() { return cloneWithValue(get()+1); }
    public ArrayIndexer previous() { return cloneWithValue(get()-1); }

    private ArrayIndexer cloneWithValue(Integer value) {
        return new ArrayIndexer(getRecorder(), getName(), value);
    }
}
