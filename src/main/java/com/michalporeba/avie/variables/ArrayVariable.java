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

    public T getAt(ArrayIndex i) { return getAt(i.get()); }
    public T getAt(int i) { return data[i]; }

    public void setAt(ArrayIndex i, T value) { setAt(i.get(), value); }
    public void setAt(int i, ScalarVariable value) { setAt(i, (T)value.get()); }
    public void setAt(int i, T value) { data[i] = value; }

    public void move(ArrayIndex from, ArrayIndex to) { move(from.get(), to.get()); }
    public void move(ArrayIndex from, int to) { move(from.get(), to); }
    public void move(int from, int to) { data[to] = data[from]; }

    public int size() {
        return data.length;
    }

    public interface Recorder {
        void read(ArrayVariable variable, ArrayIndex index);
        void write(ArrayVariable variable, ArrayIndex index, Object value);
    }
}
