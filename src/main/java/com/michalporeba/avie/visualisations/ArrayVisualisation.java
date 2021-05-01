package com.michalporeba.avie.visualisations;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ArrayVisualisation<T extends Number> {
    private final Pane pane;
    private Rectangle[] arrayNodes;

    public ArrayVisualisation(Pane visualisationPane) {
        visualisationPane.setStyle("-fx-background-color: green");
        this.pane = new Pane();
        this.pane.prefHeightProperty().bind(visualisationPane.heightProperty());
        this.pane.prefWidthProperty().bind(visualisationPane.widthProperty());
        this.pane.maxWidthProperty().bind(visualisationPane.widthProperty());
        this.pane.minWidthProperty().bind(visualisationPane.widthProperty());
        visualisationPane.getChildren().add(pane);
        this.pane.setStyle("-fx-border-color: black");
        visualisationPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(() -> {
                    System.out.println(newValue);
                    pane.prefWidth(100);
                });
            }
        });
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
            r.xProperty().bind(pane.widthProperty().divide(l).multiply(i));
            r.setStroke(Color.RED);
            r.setStrokeWidth(10);
            arrayNodes[i] = r;
            pane.getChildren().add(r);
        }

    }
}
