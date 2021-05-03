package com.michalporeba.avie.visualisations;

public interface ArrayVisualisation {
    void reset();

    void registerIndexer(String name);
    void registerVariable(String name);
    void setData(String name, int[] data);
    void setName(String name);
}
