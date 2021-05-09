package com.michalporeba.avie.visualisations;

import javafx.application.Platform;
import javafx.css.*;
import javafx.scene.layout.Pane;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayVisualisationWithGraph
    extends Pane implements ArrayVisualisation
{
    private static final StyleablePropertyFactory<ArrayVisualisationWithGraph> STYLE_FACTORY
            = new StyleablePropertyFactory<>(ArrayVisualisationWithGraph.getClassCssMetaData());

    private final StyleableProperty<Number> arrayOffset
            = STYLE_FACTORY.createStyleableNumberProperty(this, "arrayOffset", "-c-array-offset", x -> x.arrayOffset);

    private final double MAX_VALUE_WIDTH = 50;
    private final double MIN_VALUE_WIDTH = 10;

    private final Map<String, ArrayValueGraph> variables = new HashMap<>();
    private ArrayValueGraph[] data = new ArrayValueGraph[0];
    private int maxValue = 0;

    public ArrayVisualisationWithGraph() {
        this.widthProperty().addListener(o -> resize());
        this.heightProperty().addListener(o -> resize());
        this.getStyleClass().add("array-view");
        System.out.println("Array offset: " + this.arrayOffset.getValue());

        this.widthProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            System.out.println(newValue);
            if (oldValue.doubleValue() < 600 && newValue.doubleValue() > 600) {
                data[(newValue.intValue() % data.length)].moveValueTo(variables.get("k"));
            }
            refresh();
        }));
    }

    private void resize() {
        this.setPrefWidth(this.getWidth());
        this.setPrefHeight(this.getHeight());
        this.setMinWidth(0);
        this.setMinHeight(0);
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
            v.setX(getPadding().getLeft() + i * valueWidth);
            v.setY(getPadding().getTop());
            v.setHeight(getAvailableHeight());
            v.refresh();
            ++i;
        }
    }

    private double getAvailableHeight() {
        return getHeight()
                - getPadding().getTop()
                - getPadding().getBottom();
    }

    private double getAvailableWidth() {
        return getWidth() - getPadding().getLeft() - getPadding().getRight();
    }

    private void refreshArray(double valueWidth) {
        double arrayLeft = getArrayLeft();
        for(int i = 0; i < data.length; ++i) {
            data[i].setWidth(valueWidth);
            data[i].setX(arrayLeft + i * valueWidth);
            data[i].setY(getPadding().getTop());
            data[i].setHeight(getAvailableHeight());
            data[i].refresh();
        }
    }

    private void refreshIndexers(double valueWidth) {
        double arrayLeft = getArrayLeft();

    }

    private double getArrayLeft() {
        return getPadding().getLeft()
                + getValueWidth() * variables.size()
                + arrayOffset.getValue().doubleValue();
    }

    private double getValueWidth() {
        int values = variables.size() + data.length;
        if (data == null || values == 0) return MIN_VALUE_WIDTH;

        double valueWidth = (getAvailableWidth() - arrayOffset.getValue().doubleValue()) / values ;

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
        variables.put(name, new ArrayValueGraph(this, name, maxValue));
        refresh();
    }

    public void setData(String name, int[] data) {
        maxValue = Arrays.stream(data).max().getAsInt();
        for(var v : variables.values()) {
            v.setMaxValue(maxValue);
        }
        this.data = new ArrayValueGraph[data.length];
        for(int i = 0; i < data.length; ++i) {
            this.data[i] = new ArrayValueGraph(this, Integer.valueOf(i).toString(), maxValue);
            this.data[i].setValue(data[i]);
        }
        refresh();
    }

    public void setName(String name) {
        refresh();
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return STYLE_FACTORY.getCssMetaData();
    }
}