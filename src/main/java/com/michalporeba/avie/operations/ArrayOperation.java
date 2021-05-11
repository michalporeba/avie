package com.michalporeba.avie.operations;

public class ArrayOperation extends Operation {
    private final String arrayName;
    private final Integer index;
    public ArrayOperation(String array, Integer index) {
        this.arrayName = array;
        this.index = index;
    }

    public String getArrayName() { return this.arrayName; }
    public Integer getIndex() { return this.index; }
}
