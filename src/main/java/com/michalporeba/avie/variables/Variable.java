package com.michalporeba.avie.variables;

public abstract class Variable<T> {
    private final AccessRecorder recorder;
    private final String name;
    private T value;

    protected Variable(AccessRecorder recorder, String name, T value) {
        this.name = name;
        this.recorder = recorder;
    }

    public String getName() {
        return name;
    }
    public T getValue() {
        recorder.read(this);
        return this.value;
    }

    public T getValueWithoutLogging() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
        recorder.write(this);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
