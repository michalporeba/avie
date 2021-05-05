package com.michalporeba.avie.visualisations;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ArrayVisualisationWithGraph
    extends Region implements ArrayVisualisation
{
    private final double CONTROL_PADDING = 15;
    private final double ARRAY_PADDING = 30;
    private final double MAX_VALUE_WIDTH = 50;
    private final double MIN_VALUE_WIDTH = 10;
    private final double VALUE_MARGIN = 2;
    private final double FONT_SIZE = 20;

    private final Pane pane;
    private final Map<String, ValueGraph> variables = new HashMap<>();
    private ValueGraph[] data = new ValueGraph[0];
    private int maxValue = 0;

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
        if (data == null) return; // there is no data yet, so nothing to display

        double valueWidth = getValueWidth();

        refreshVariables(valueWidth);

        refreshArray(valueWidth);
    }

    private void refreshVariables(double valueWidth) {
        int i = 0;
        for(ValueGraph v : variables.values()) {
            v.setWidth(valueWidth);
            v.setX(CONTROL_PADDING + i * valueWidth);
            v.setY(CONTROL_PADDING);
            v.setHeight(pane.getHeight() - 2 * CONTROL_PADDING);
            v.refresh();
            ++i;
        }
    }

    private void refreshArray(double valueWidth) {
        double arrayLeft = getArrayLeft();
        for(int i = 0; i < data.length; ++i) {
            data[i].setWidth(valueWidth);
            data[i].setX(arrayLeft + i * valueWidth);
            data[i].setY(CONTROL_PADDING);
            data[i].setHeight(pane.getHeight() - 2 * CONTROL_PADDING);
            data[i].refresh();
        }
    }

    private void refreshIndexers(double valueWidth) {
        double arrayLeft = getArrayLeft();

    }

    private double getArrayLeft() {
        return CONTROL_PADDING + getValueWidth() * variables.size() + ARRAY_PADDING;
    }

    private double getValueWidth() {
        int values = variables.size() + data.length;
        if (data == null || values == 0) return MIN_VALUE_WIDTH;

        double availableWidth = pane.getWidth() - (2 * CONTROL_PADDING) - ARRAY_PADDING;
        double valueWidth = availableWidth / values;

        if (valueWidth < MIN_VALUE_WIDTH) return MIN_VALUE_WIDTH;
        if (valueWidth > MAX_VALUE_WIDTH) return MAX_VALUE_WIDTH;
        return valueWidth;
    }

    public void reset() {
        refresh();
    }

    public void registerIndexer(String name) {
        refresh();
    }

    public void registerVariable(String name) {
        variables.put(name, new ValueGraph(pane, name, maxValue));
        refresh();
    }

    public void setData(String name, int[] data) {
        maxValue = Arrays.stream(data).max().getAsInt();
        for(ValueGraph v : variables.values()) {
            v.setMaxValue(maxValue);
        }
        this.data = new ValueGraph[data.length];
        for(int i = 0; i < data.length; ++i) {
            this.data[i] = new ValueGraph(pane, Integer.valueOf(i).toString(), maxValue);
            this.data[i].setValue(data[i]);
        }
        refresh();
    }

    public void setName(String name) {
        refresh();
    }

    private class ValueGraph {
        private Rectangle arrayBox = new Rectangle();
        private Rectangle valueBox = new Rectangle();
        private Text label;
        private double value = 0;
        private double x = 0;
        private double y = 0;
        private double height = 0;
        private double width = 0;
        private double maxValue = 0;


        public ValueGraph(Pane pane, String name, int maxValue) {
            this.maxValue = maxValue;
            arrayBox.setY(100);
            arrayBox.setStroke(Color.RED);
            arrayBox.setStrokeWidth(3);
            pane.getChildren().add(arrayBox);

            valueBox.setStroke(Color.GRAY);
            valueBox.setStrokeWidth(2);
            valueBox.setFill(Color.LIGHTBLUE);
            pane.getChildren().add(valueBox);

            label = new Text(name);
            label.setFont(Font.font ("Verdana", FONT_SIZE));
            label.setFill(Color.RED);
            pane.getChildren().add(label);
        }

        public void setX(double x) { this.x = x; }
        public void setY(double y) { this.y = y; }
        public void setHeight(double height) { this.height = height; }
        public void setWidth(double width) { this.width = width; }
        public void setValue(int value) { this.value = value; }
        public void setMaxValue(int value) { this.maxValue = value; }

        public void refresh() {
            arrayBox.setX(x+VALUE_MARGIN);
            arrayBox.setY(y+height - width - 2 * VALUE_MARGIN);
            arrayBox.setWidth(width - 2 * VALUE_MARGIN);
            arrayBox.setHeight(width);

            label.setLayoutX(arrayBox.getX() + arrayBox.getWidth()/2 - label.getLayoutBounds().getWidth()/2);
            label.setLayoutY(arrayBox.getY() + arrayBox.getHeight()/2);

            valueBox.setX(x+VALUE_MARGIN);
            valueBox.setWidth(width - 2 * VALUE_MARGIN);

            double availableHeight = arrayBox.getY() - y - 3 * VALUE_MARGIN;
            double valueHeight = availableHeight * (value / maxValue);
            valueBox.setY(y+VALUE_MARGIN + availableHeight - valueHeight);

            valueBox.setHeight(valueHeight);
        }
    }
}
