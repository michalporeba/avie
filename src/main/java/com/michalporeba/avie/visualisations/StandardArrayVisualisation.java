package com.michalporeba.avie.visualisations;

import javafx.application.Platform;
import javafx.css.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardArrayVisualisation
    extends Pane implements ArrayVisualisation
{
    private static final StyleablePropertyFactory<StandardArrayVisualisation> STYLE_FACTORY
            = new StyleablePropertyFactory<>(StandardArrayVisualisation.getClassCssMetaData());

    private final StyleableProperty<Number> arrayOffset
            = STYLE_FACTORY.createStyleableNumberProperty(this, "arrayOffset", "-av-array-offset", x -> x.arrayOffset);

    private final StyleableProperty<Number> minValueWidth
            = STYLE_FACTORY.createStyleableNumberProperty(this, "minValueWidth", "-av-min-value-width", x -> x.minValueWidth);

    private final StyleableProperty<Number> maxValueWidth
            = STYLE_FACTORY.createStyleableNumberProperty(this, "maxValueWidth", "-av-max-value-width", x -> x.maxValueWidth);

    private final Map<String, ArrayValueGraph> variables = new HashMap<>();
    private ArrayValueGraph[] data = new ArrayValueGraph[0];
    private int maxValue = 0;
    private final Text name = new Text();

    public StandardArrayVisualisation() {
        this.minValueWidth.setValue(10);
        this.maxValueWidth.setValue(50);
        this.widthProperty().addListener(o -> resize());
        this.heightProperty().addListener(o -> resize());
        this.getStyleClass().add("array-view");

        this.name.getStyleClass().add("name");
        this.name.setLayoutX(30);

        System.out.println("Padding: " + getPadding().getLeft());

        this.name.setLayoutY(30+this.name.getLayoutBounds().getHeight()*1.5);
        this.getChildren().add(this.name);

        // temporary only for development
        this.widthProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            System.out.println(newValue);
            if (oldValue.doubleValue() < 600 && newValue.doubleValue() > 600) {
                var from = (int)(Math.random() * data.length);
                var to = (int)(Math.random() * data.length);
                if (from == to) {
                    data[from].moveValueTo(variables.get("k"));
                    variables.get("k").moveValueTo(data[from]);
                } else {
                    data[from].moveValueTo(data[to]);
                    data[to].moveValueTo(data[from]);
                }
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

        this.name.toFront();
    }

    private void refreshVariables(double valueWidth) {
        int i = 0;
        for(var v : variables.values()) {
            v.setPrefWidth(valueWidth);
            v.setLayoutX(getPadding().getLeft() + i * valueWidth);
            v.setLayoutY(getPadding().getTop());
            v.setPrefHeight(getAvailableHeight());
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
            data[i].setPrefWidth(valueWidth);
            data[i].setLayoutX(arrayLeft + i * valueWidth);
            data[i].setLayoutY(getPadding().getTop());
            data[i].setPrefHeight(getAvailableHeight());
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
        var values = variables.size() + data.length;
        var minWidth = minValueWidth.getValue().doubleValue();
        var maxWidth = maxValueWidth.getValue().doubleValue();

        if (data == null || values == 0) return minWidth;

        double valueWidth = (getAvailableWidth() - arrayOffset.getValue().doubleValue()) / values ;

        if (valueWidth < minWidth) return minWidth;
        if (valueWidth > maxWidth) return maxWidth;
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
        this.name.setText(name);
        refresh();
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return STYLE_FACTORY.getCssMetaData();
    }
}