package com.michalporeba.avie.operations;

public class VariableSet extends Operation {
    private final String variable;
    private final Object value;

    public VariableSet(String variable, Object value) {
        this.variable = variable;
        this.value = value;
    }

    public String getVariableName() {
        return this.variable;
    }

    public Object getValue() {
        return this.value;
    }
}
