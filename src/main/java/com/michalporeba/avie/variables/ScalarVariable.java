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

    public interface Recorder {
        void read(ScalarVariable variable);
        void write(ScalarVariable variable, Object value);
    }

    protected Recorder getRecorder() {
        return this.recorder;
    }
}
