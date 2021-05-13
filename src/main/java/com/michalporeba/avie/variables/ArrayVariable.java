package com.michalporeba.avie.variables;

public class ArrayVariable extends Variable{
    private int[] data;
    private final Recorder recorder;

    public ArrayVariable(Recorder recorder, String name) {
        super(name);
        this.recorder = recorder;
    }

    public void set(int[] data) {
        this.data = data.clone();
    }
    public int[] get() { return this.data.clone(); }

    public int getAt(ArrayIndexer i) { return getAt(i.get()); }
    public int getAt(int i) { return data[i]; }

    public void setAt(ArrayIndexer i, ScalarVariable value) {
        data[i.get()] = value.get();
        recorder.write(this, i, value.get());
    }

    public void copy(ArrayIndexer from, ArrayIndexer to) {
        data[to.get()] = data[from.get()];
        recorder.copy(this, from, to);
    }

    public int size() {
        return data.length;
    }

    public interface Recorder {
        default void read(ArrayVariable array, ArrayIndexer index) {}
        default void write(ArrayVariable array, ArrayIndexer index, Object value) {}
        default void copy(ArrayVariable array, ArrayIndexer from, ArrayIndexer to) {}
    }
}
