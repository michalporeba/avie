package com.michalporeba.avie.visualisations;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArrayVisualisationWithGraph
    extends Region implements ArrayVisualisation
{
    private final double ARRAY_PADDING = 30;
    private final double MAX_VALUE_WIDTH = 50;
    private final double MIN_VALUE_WIDTH = 10;

    private final Pane pane;
    private final Map<String, ArrayValueGraph> variables = new HashMap<>();
    private ArrayValueGraph[] data = new ArrayValueGraph[0];
    private int maxValue = 0;

    public ArrayVisualisationWithGraph() {
        this.widthProperty().addListener(o -> resize());
        this.heightProperty().addListener(o -> resize());
        this.pane = new Pane();
        this.pane.getStyleClass().add("array-view");
        this.getChildren().add(pane);
        this.widthProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            System.out.println(newValue);
            if (oldValue.doubleValue() < 600 && newValue.doubleValue() > 600) {
                data[(newValue.intValue() % data.length)].moveValueTo(variables.get("k"));
            }
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
        for(var v : variables.values()) {
            v.setWidth(valueWidth);
            v.setX(pane.getPadding().getLeft() + i * valueWidth);
            v.setY(pane.getPadding().getTop());
            v.setHeight(getAvailableHeight());
            v.refresh();
            ++i;
        }
    }

    private double getAvailableHeight() {
        return pane.getHeight()
                - pane.getPadding().getTop()
                - pane.getPadding().getBottom();
    }

    private double getAvailableWidth() {
        return pane.getWidth() - pane.getPadding().getLeft() - pane.getPadding().getRight();
    }

    private void refreshArray(double valueWidth) {
        double arrayLeft = getArrayLeft();
        for(int i = 0; i < data.length; ++i) {
            data[i].setWidth(valueWidth);
            data[i].setX(arrayLeft + i * valueWidth);
            data[i].setY(pane.getPadding().getTop());
            data[i].setHeight(getAvailableHeight());
            data[i].refresh();
        }
    }

    private void refreshIndexers(double valueWidth) {
        double arrayLeft = getArrayLeft();

    }

    private double getArrayLeft() {
        return pane.getPadding().getLeft()
                + getValueWidth() * variables.size()
                + ARRAY_PADDING;
    }

    private double getValueWidth() {
        int values = variables.size() + data.length;
        if (data == null || values == 0) return MIN_VALUE_WIDTH;

        double valueWidth = (getAvailableWidth() - ARRAY_PADDING) / values ;

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
        variables.put(name, new ArrayValueGraph(pane, name, maxValue));
        refresh();
    }

    public void setData(String name, int[] data) {
        maxValue = Arrays.stream(data).max().getAsInt();
        for(var v : variables.values()) {
            v.setMaxValue(maxValue);
        }
        this.data = new ArrayValueGraph[data.length];
        for(int i = 0; i < data.length; ++i) {
            this.data[i] = new ArrayValueGraph(pane, Integer.valueOf(i).toString(), maxValue);
            this.data[i].setValue(data[i]);
        }
        refresh();
    }

    public void setName(String name) {
        refresh();
    }
}