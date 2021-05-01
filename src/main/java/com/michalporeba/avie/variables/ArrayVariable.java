package com.michalporeba.avie.variables;

public class ArrayVariable<T extends Number> extends Variable{
    private T[] data;

    public ArrayVariable(Recorder recorder, String name) {
        super(name);
    }

    public void set(T[] data) {
        this.data = data.clone();
    }
    public T[] get() { return this.data.clone(); }

    public T getAt(ArrayIndexer i) { return getAt(i.get()); }
    public T getAt(int i) { return data[i]; }

    public void setAt(ArrayIndexer i, ScalarVariable<T> value) {
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
