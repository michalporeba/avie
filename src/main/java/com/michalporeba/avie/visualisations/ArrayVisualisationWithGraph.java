package com.michalporeba.avie.visualisations;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class ArrayVisualisationWithGraph
    extends Region implements ArrayVisualisation
{
    private final Pane pane;
    private final Map<String, ValueGraph> variables = new HashMap<>();
    private ValueGraph[] data;

    public ArrayVisualisationWithGraph() {
        this.widthProperty().addListener(o -> resize());
        this.heightProperty().addListener(o -> resize());
        this.pane = new Pane();
        this.pane.setStyle("-fx-border-color: blue");
        this.getChildren().add(pane);
        this.widthProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            System.out.println(newValue);
            refresh();
        }));
    }

    private void resize() {
        this.pane.setPrefWidth(this.getWidth());
        this.pane.setPrefHeight(this.getHeight());
        this.pane.setMinWidth(0);
        this.pane.setMinHeight(0);
        refresh();
    }

    private void refresh() {
        double width = 0;
        if (data == null) return;

        double size = (pane.getWidth()-20) / (data.length + variables.size() + 1);
        if(size > 50) size = 50;

        int mi = 0;
        for(Map.Entry<String, ValueGraph> e : variables.entrySet()) {
            e.getValue().setSize(size - 10);
            e.getValue().setX((0.5+mi)*size);
            mi++;
        }

        for(int i = 0; i < data.length; ++i) {
            data[i].setSize(size - 10);
            data[i].setX((1 + variables.size() + i)*size);
        }
    }

    private void refreshVariables(double size) {

    }

    public void reset() {
        refresh();
    }

    public void registerIndexer(String name) {
        refresh();
    }

    public void registerVariable(String name) {
        variables.put(name, new ValueGraph(pane, name));
        refresh();
    }

    public void setData(String name, int[] data) {
        this.data = new ValueGraph[data.length];
        for(int i = 0; i < data.length; ++i) {
            this.data[i] = new ValueGraph(pane, Integer.valueOf(i).toString());
        }
        refresh();
    }

    public void setName(String name) {
        refresh();
    }

    private class ValueGraph {
        private Rectangle rectangle = new Rectangle();
        private Text label;
        public ValueGraph(Pane pane, String name) {
            label = new Text(name);
            rectangle.setY(100);
            rectangle.setHeight(40);
            rectangle.setStroke(Color.RED);
            rectangle.setStrokeWidth(3);
            pane.getChildren().add(rectangle);
            label.setLayoutY(130);
            label.setFont(Font.font ("Verdana", 20));
            label.setFill(Color.RED);
            pane.getChildren().add(label);
        }

        public void setX(double x) {
            rectangle.setX(x);
            label.setLayoutX(x+rectangle.getWidth()/2-label.getLayoutBounds().getWidth()/2);
        }

        public void setSize(double size) {
            rectangle.setWidth(size);
            rectangle.setHeight(size);
            label.setLayoutY(120 + size/2 - label.getLayoutBounds().getHeight()/2);
        }
    }
}
