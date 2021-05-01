package com.michalporeba.avie.visualisations;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ArrayVisualisation<T extends Number> {
    private final Pane pane;
    private Rectangle[] arrayNodes;

    public ArrayVisualisation(Pane visualisationPane) {
        this.pane = visualisationPane;
        this.pane.setStyle("-fx-border-color: black");
    }

    public void show(T[] data) {
        int l = data.length;
        arrayNodes = new Rectangle[l];
        double w = 600;
        double size = w/l;

        for(int i = 0; i < l; ++i) {
            Rectangle r = new Rectangle();
            //r.resize(size*0.9, size*0.9);
            r.setY(300/2-(size*0.9/2));
            //r.setX(50+i*50);
            r.setX(i*size);
            r.setStroke(Color.RED);
            r.setStrokeWidth(10);
            arrayNodes[i] = r;
            pane.getChildren().add(r);
        }

    }
}
