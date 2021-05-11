package com.michalporeba.avie.visualisations;

import com.michalporeba.avie.operations.Operation;

public interface Visualisation {
    void reset();
    void setName(String name);
    void show(Operation operation);
}
