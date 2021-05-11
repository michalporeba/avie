package com.michalporeba.avie.operations;

public class ArrayToScalar extends ArrayOperation {
    private final String variableName;
    public ArrayToScalar(String array, Integer index, String variable) {
        super(array, index);
        this.variableName = variable;
    }

    public String getVariableName() { return this.variableName; }
}
