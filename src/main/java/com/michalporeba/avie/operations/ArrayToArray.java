package com.michalporeba.avie.operations;

public class ArrayToArray extends Operation {
    private final ArrayOperation source;
    private final ArrayOperation destination;

    public ArrayToArray(String sourceArray, Integer sourceIndex, String destinationArray, Integer destinationIndex) {
        source = new ArrayOperation(sourceArray, sourceIndex);
        destination = new ArrayOperation(destinationArray, destinationIndex);
    }

    public ArrayOperation getSource() { return source; }
    public ArrayOperation getDestination() { return destination; }
}
