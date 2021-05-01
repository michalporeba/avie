package com.michalporeba.avie.variables;

public abstract class ScalarVariable<T> extends Variable {
    private T value;
    private final Recorder recorder;

    public ScalarVariable(Recorder recorder, String name, T value) {
        super(name);
        this.recorder = recorder;
        this.value = value;
    }

    public T get() {
        recorder.read(this);
        return this.value;
    }

    public void set(T value) {
        recorder.write(this, value);
        this.value = value;
    }

    public interface Recorder {
        void read(ScalarVariable variable);
        void write(ScalarVariable variable, Object value);
    }
}
