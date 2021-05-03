package com.michalporeba.avie.visualisations;

import javafx.scene.layout.Region;

public abstract class ArrayVisualisation<T extends Number> extends Region {
    public abstract void show(T[] data);
}
