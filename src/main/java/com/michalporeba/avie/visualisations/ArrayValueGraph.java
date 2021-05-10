package com.michalporeba.avie.visualisations;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.List;

class ArrayValueGraph extends Region {
    private final Pane parent;
    private Rectangle arrayBox = new Rectangle();
    private Rectangle valueBox = new Rectangle();
    private Text label;
    private double value = 0;
    private double maxValue = 0;

    private static final StyleablePropertyFactory<ArrayValueGraph> STYLE_FACTORY
            = new StyleablePropertyFactory<>(ArrayValueGraph.getClassCssMetaData());

    private final StyleableProperty<Number> valueOffset
            = STYLE_FACTORY.createStyleableNumberProperty(this, "valueOffset", "-av-value-offset", x -> x.valueOffset);

    public ArrayValueGraph(Pane parent, String name, int maxValue) {
        this.parent = parent;
        this.parent.getChildren().add(this);
        this.getStyleClass().add("value-graph");
        this.maxValue = maxValue;

        arrayBox.getStyleClass().add("variable");
        arrayBox.setY(100);
        getChildren().add(arrayBox);

        valueBox.getStyleClass().add("value");
        getChildren().add(valueBox);

        label = new Text(name);
        label.getStyleClass().add("label");
        getChildren().add(label);
    }

    public void setValue(int value) {
        this.value = value;
        valueBox.getStyleClass().add("active");
        valueBox.getStyleClass().remove("stale");
        valueBox.setOpacity(1);
    }

    public void setMaxValue(int value) {
        this.maxValue = value;
    }

    public double getValuePositionX() {
        return getLayoutX() + this.valueBox.getX();
    }

    private Rectangle getValueRectangle() {
        var rectangle = new Rectangle(
                getLayoutX() + valueBox.getX()
                , getLayoutY() + valueBox.getY()
                , valueBox.getWidth(), valueBox.getHeight()
        );
        rectangle.getStyleClass().add("moving-value");
        return rectangle;
    }

    public void refresh() {
        arrayBox.setX(getPadding().getLeft());
        arrayBox.setY(getPrefHeight() - getPrefWidth() - getPadding().getBottom());
        arrayBox.setWidth(getAvailableWidth());
        arrayBox.setHeight(getPrefWidth());

        label.setLayoutX(arrayBox.getX() + arrayBox.getWidth() / 2 - label.getLayoutBounds().getWidth() / 2);
        label.setLayoutY(arrayBox.getY() + arrayBox.getHeight() / 2 + label.getLayoutBounds().getHeight() / 3);

        valueBox.setX(getPadding().getLeft());
        valueBox.setWidth(getAvailableWidth());

        double valueHeight = getMaxValueHeight() * (value / maxValue);
        valueBox.setY(getPadding().getTop() + getMaxValueHeight() - valueHeight);

        valueBox.setHeight(valueHeight);
    }

    private double getAvailableWidth() {
        return getPrefWidth() - getPadding().getLeft() - getPadding().getRight();
    }

    private double getMaxValueHeight() {
        return arrayBox.getY() - getPadding().getTop() - valueOffset.getValue().doubleValue();
    }

    public void setStale() {
        valueBox.getStyleClass().remove("active");
        valueBox.getStyleClass().add("stale");
    }

    public void moveValueTo(ArrayValueGraph destination) {
        var m = getValueRectangle();
        var currentValue = Integer.valueOf((int)value);
        parent.getChildren().add(m);
        setStale();

        var t = new TranslateTransition();
        t.setDuration(Duration.millis(1000));
        t.setNode(m);
        this.setStale();
        t.setByX(destination.getValuePositionX() - (getLayoutX() + valueBox.getX()));
        t.statusProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Animation.Status.STOPPED) {
                destination.setValue(currentValue);
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

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return STYLE_FACTORY.getCssMetaData();
    }
}
