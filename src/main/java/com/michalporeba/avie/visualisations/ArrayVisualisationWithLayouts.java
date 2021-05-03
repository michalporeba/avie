package com.michalporeba.avie.visualisations;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ArrayVisualisationWithLayouts<T extends Number>
    extends ArrayVisualisation<T>
{
    private HBox pane;
    private Circle[] arrayNodes;

    public ArrayVisualisationWithLayouts() {
        this.minHeight(50);
        this.minWidth(50);
        this.widthProperty().addListener(o -> resize());
        this.heightProperty().addListener(o -> resize());
        this.pane = new HBox();
        this.pane.setStyle("-fx-background-color: deeppink");
        this.pane.setStyle("-fx-border-color: blue");
        this.getChildren().add(pane);
        this.widthProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            System.out.println("layout: " + newValue);
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
        arrayNodes = new Circle[l];
        double w = 600;
        double size = w/l;

        for(int i = 0; i < l; ++i) {
            Circle c = new Circle();
            c.setRadius(20);
            c.setStroke(Color.RED);
            c.setStrokeWidth(3);
            arrayNodes[i] = c;
            pane.getChildren().add(c);
        }
        adjust();
    }

    private void adjust() {
        double size = (pane.getWidth()-20) / arrayNodes.length;
        if(size > 50) size = 50;

        for(int i = 0; i < arrayNodes.length; ++i) {
            arrayNodes[i].setRadius(size/2);
        }
    }
}
