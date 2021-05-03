package com.michalporeba.avie.visualisations;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ArrayVisualisationWithGraph<T extends Number>
    extends ArrayVisualisation<T>
{
    private final Pane pane;
    private Rectangle[] arrayNodes;

    public ArrayVisualisationWithGraph() {
        this.widthProperty().addListener(o -> resize());
        this.heightProperty().addListener(o -> resize());
        this.pane = new Pane();
        this.pane.setStyle("-fx-border-color: blue");
        this.getChildren().add(pane);
        this.widthProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            System.out.println(newValue);
            adjust();
        }));
    }

    private void resize() {
        this.pane.setPrefWidth(this.getWidth());
        this.pane.setPrefHeight(this.getHeight());
        this.pane.setMinWidth(0);
        this.pane.setMinHeight(0);
        adjust();
    }

    public void show(T[] data) {
        int l = data.length;
        arrayNodes = new Rectangle[l];
        double w = 600;
        double size = w/l;

        for(int i = 0; i < l; ++i) {
            Rectangle r = new Rectangle();
            r.setX(i*size);
            r.setHeight(40);
            r.setStroke(Color.RED);
            r.setStrokeWidth(3);
            arrayNodes[i] = r;
            pane.getChildren().add(r);
        }
        adjust();
    }

    private void adjust() {
        double size = (pane.getWidth()-20) / arrayNodes.length;
        if(size > 50) size = 50;

        for(int i = 0; i < arrayNodes.length; ++i) {
            arrayNodes[i].setWidth(size - 10);
            arrayNodes[i].setX(10+i*size);
            arrayNodes[i].setY(pane.getHeight()-50);
        }
    }
}
