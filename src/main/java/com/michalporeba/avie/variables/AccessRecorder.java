package com.michalporeba.avie.variables;

public interface AccessRecorder {
    public void read(Variable variable);
    public void write(Variable variable);
}
