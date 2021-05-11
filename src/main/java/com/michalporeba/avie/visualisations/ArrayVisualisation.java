package com.michalporeba.avie.visualisations;

public interface ArrayVisualisation extends Visualisation {
    void registerIndexer(String name);
    void registerVariable(String name);
    void setData(String name, int[] data);
}
