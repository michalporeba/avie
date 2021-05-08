package com.michalporeba.avie.visualisations;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Arrays;
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

    private final Pane pane;
    private final Map<String, ValueGraph> variables = new HashMap<>();
    private ValueGraph[] data = new ValueGraph[0];
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
        private final Pane parent;
        private final Pane root;
        private Rectangle arrayBox = new Rectangle();
        private Rectangle valueBox = new Rectangle();
        private Text label;
        private double value = 0;
        private double maxValue = 0;

        public ValueGraph(Pane parent, String name, int maxValue) {
            this.parent = parent;
            this.root = new Pane();
            this.root.getStyleClass().add("value-graph");
            this.parent.getChildren().add(root);
            this.maxValue = maxValue;

            arrayBox.getStyleClass().add("variable");
            arrayBox.setY(100);
            root.getChildren().add(arrayBox);

            valueBox.getStyleClass().add("value");
            root.getChildren().add(valueBox);

            label = new Text(name);
            label.getStyleClass().add("label");
            root.getChildren().add(label);
        }

        public void setX(double x) { root.setLayoutX(x); }
        public void setY(double y) { root.setLayoutY(y); }
        public void setHeight(double height) { root.setPrefHeight(height); }
        public void setWidth(double width) { root.setPrefWidth(width); }
        public void setValue(int value) {
            this.value = value;
            valueBox.getStyleClass().add("active");
            valueBox.getStyleClass().remove("stale");
            valueBox.setOpacity(1);
        }
        public void setMaxValue(int value) { this.maxValue = value; }
        public double getValuePositionX() { return this.root.getLayoutX() + this.valueBox.getX(); }

        public Rectangle getValueRectangle() {
            var rectangle = new Rectangle(
                    root.getLayoutX() + valueBox.getX()
                    ,root.getLayoutY() + valueBox.getY()
                    ,valueBox.getWidth(), valueBox.getHeight()
            );
            rectangle.getStyleClass().add("moving-value");
            return rectangle;
        }

        public void refresh() {
            arrayBox.setX(VALUE_MARGIN);
            arrayBox.setY(root.getPrefHeight() - root.getPrefWidth() - 2 * VALUE_MARGIN);
            arrayBox.setWidth(root.getPrefWidth() - 2 * VALUE_MARGIN);
            arrayBox.setHeight(root.getPrefWidth());

            label.setLayoutX(arrayBox.getX() + arrayBox.getWidth()/2 - label.getLayoutBounds().getWidth()/2);
            label.setLayoutY(arrayBox.getY() + arrayBox.getHeight()/2);

            valueBox.setX(VALUE_MARGIN);
            valueBox.setWidth(root.getPrefWidth() - 2 * VALUE_MARGIN);

            double availableHeight = arrayBox.getY() - root.getLayoutY() - 3 * VALUE_MARGIN;
            double valueHeight = availableHeight * (value / maxValue);
            valueBox.setY(VALUE_MARGIN + availableHeight - valueHeight);

            valueBox.setHeight(valueHeight);
        }

        public void setStale() {
            valueBox.getStyleClass().remove("active");
            valueBox.getStyleClass().add("stale");
        }

        public void moveValueTo(ValueGraph destination) {
            var m = getValueRectangle();
            parent.getChildren().add(m);
            setStale();

            var t = new TranslateTransition();
            t.setDuration(Duration.millis(1000));
            t.setNode(m);
            this.setStale();
            t.setByX(destination.getValuePositionX() - (root.getLayoutX() + valueBox.getX()));
            t.statusProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue == Animation.Status.STOPPED) {
                    destination.setValue((int)value);
                    destination.refresh();
                    parent.getChildren().remove(m);
                }
            });
            destination.dissolve();
            t.play();
        }

        private void dissolve() {
            var t = new FadeTransition(Duration.millis(800), valueBox);
            t.setToValue(0);
            t.play();
        }
    }
}
