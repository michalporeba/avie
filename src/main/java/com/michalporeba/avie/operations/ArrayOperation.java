package com.michalporeba.avie.operations;

public class ArrayOperation extends Operation {
    private final String array;
    private final Integer index;
    public ArrayOperation(String array, Integer index) {
        this.array = array;
        this.index = index;
    }

    public String getArray() { return this.array; }
    public Integer getIndex() { return this.index; }
}
