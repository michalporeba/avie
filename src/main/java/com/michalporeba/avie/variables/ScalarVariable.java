package com.michalporeba.avie.variables;

public abstract class ScalarVariable extends Variable {
    private int value;
    private final Recorder recorder;

    public ScalarVariable(Recorder recorder, String name, int value) {
        super(name);
        this.recorder = recorder;
        this.value = value;
    }

    public int get() {
        recorder.read(this);
        return this.value;
    }

    public void set(int value) {
        recorder.write(this, value);
        this.value = value;
    }

    private void set(ScalarVariable variable) {
        set(variable.get());
    }

    public void take(ArrayVariable array, ArrayIndexer index) {
        recorder.copy(array, index, this);
        this.value = array.getAt(index);
    }

    public void set(ArrayVariable array, ArrayIndexer index) {
        recorder.copy(this, array, index);
        array.setAt(index, this);
    }

    public interface Recorder {
        default void read(ScalarVariable variable) {}
        default void write(ScalarVariable variable, Object value) {}
        default void copy(ArrayVariable array, ArrayIndexer index, ScalarVariable variable) {}
        default void copy(ScalarVariable variable, ArrayVariable array, ArrayIndexer index) {}
    }

    protected Recorder getRecorder() {
        return this.recorder;
    }
}
