package com.michalporeba.avie.operations;

public class ArrayToScalar extends ArrayOperation {
    private final String variable;
    public ArrayToScalar(String array, Integer index, String variable) {
        super(array, index);
        this.variable = variable;
    }

    public String getVariable() { return this.variable; }
}
