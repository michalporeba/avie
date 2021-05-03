package com.michalporeba.avie.variables;

public class ArrayVariable extends Variable{
    private int[] data;

    public ArrayVariable(Recorder recorder, String name) {
        super(name);
    }

    public void set(int[] data) {
        this.data = data.clone();
    }
    public int[] get() { return this.data.clone(); }

    public int getAt(ArrayIndexer i) { return getAt(i.get()); }
    public int getAt(int i) { return data[i]; }

    public void setAt(ArrayIndexer i, ScalarVariable value) {
        data[i.get()] = value.get();
    }

    public void move(ArrayIndexer from, ArrayIndexer to) {
        data[to.get()] = data[from.get()];
    }

    public int size() {
        return data.length;
    }

    public interface Recorder {
        void read(ArrayVariable variable, ArrayIndexer index);
        void write(ArrayVariable variable, ArrayIndexer index, Object value);
    }
}
