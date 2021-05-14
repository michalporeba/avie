package com.michalporeba.avie.operations;

public class ArraySwap extends Operation {
    private final ArrayOperation a;
    private final ArrayOperation b;

    public ArraySwap(String aArray, Integer aIndex, String bArray, Integer bIndex) {
        a = new ArrayOperation(aArray, aIndex);
        b = new ArrayOperation(bArray, bIndex);
    }

    public ArrayOperation getA() { return a; }
    public ArrayOperation getB() { return b; }
}
