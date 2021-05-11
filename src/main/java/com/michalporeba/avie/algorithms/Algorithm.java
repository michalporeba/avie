package com.michalporeba.avie.algorithms;

import com.michalporeba.avie.operations.Operation;

public interface Algorithm
        extends Iterable<Operation> {
    String getName();
    boolean isComplete();
    void progress();
}