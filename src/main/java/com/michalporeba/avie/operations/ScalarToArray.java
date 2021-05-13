package com.michalporeba.avie.operations;

public class ScalarToArray extends ArrayOperation {
    private final String variable;
    public ScalarToArray(String array, Integer index, String variable) {
        super(array, index);
        this.variable = variable;
    }

    public String getVariable() { return this.variable; }
}
