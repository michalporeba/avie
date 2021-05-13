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
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

class ArrayValueGraph extends Region {
    private final Pane parent;
    private Rectangle arrayBox = new Rectangle();
    private Rectangle valueBox = new Rectangle();
    private Polygon primaryMarker = new Polygon();
    private Polygon secondaryMarker = new Polygon();
    private boolean primaryMarkerOn = false;
    private boolean secondaryMarkerOn = false;

    private Text label;
    private double currentValue = 0;
    private double displayValue = 0;
    private final Queue<Integer> values = new LinkedBlockingQueue<>();
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

        primaryMarker.getStyleClass().add("marker");
        primaryMarker.setOpacity(0);
        getChildren().add(primaryMarker);
        secondaryMarker.getStyleClass().add("marker");
        secondaryMarker.setOpacity(0);
        getChildren().add(secondaryMarker);

        setFresh();
    }

    public void setValue(int value) {
        this.currentValue = value;
        this.displayValue = value;
    }

    public void setMaxValue(int value) {
        this.maxValue = value;
    }

    public double getValuePositionX() {
        return getLayoutX() + this.valueBox.getX();
    }

    private Rectangle getValueRectangle() {
        double valueHeight = getValueHeight(this.currentValue);
        var rectangle = new Rectangle(
                getLayoutX() + valueBox.getX()
                , getLayoutY() + getPadding().getTop() + getMaxValueHeight() - valueHeight
                , valueBox.getWidth(), valueHeight
        );
        rectangle.getStyleClass().add("moving-value");
        return rectangle;
    }

    public void refresh() {
        refreshValue();

        arrayBox.setX(getPadding().getLeft());
        arrayBox.setY(getPrefHeight() - getPrefWidth() - getPadding().getBottom());
        arrayBox.setWidth(getAvailableWidth());
        arrayBox.setHeight(getPrefWidth());

        label.setLayoutX(arrayBox.getX() + arrayBox.getWidth() / 2 - label.getLayoutBounds().getWidth() / 2);
        label.setLayoutY(arrayBox.getY() + arrayBox.getHeight() / 2 + label.getLayoutBounds().getHeight() / 3);

        valueBox.setX(getPadding().getLeft());
        valueBox.setWidth(getAvailableWidth());

        primaryMarker.getPoints().clear();
        primaryMarker.getPoints().addAll(new Double[] {
           arrayBox.getX(), arrayBox.getY(),
                arrayBox.getX(), arrayBox.getY() + arrayBox.getHeight()/6,
           arrayBox.getX() + arrayBox.getWidth()/3, arrayBox.getY()+arrayBox.getHeight()/3,
           arrayBox.getX() + arrayBox.getWidth(), arrayBox.getY()
        });

        secondaryMarker.getPoints().clear();
        secondaryMarker.getPoints().addAll(new Double[] {
                arrayBox.getX(), arrayBox.getY() + arrayBox.getHeight(),
                arrayBox.getX() + arrayBox.getWidth()/3*2, arrayBox.getY()+arrayBox.getHeight()/3*2,
                arrayBox.getX() + arrayBox.getWidth(), arrayBox.getY() + arrayBox.getHeight()/6*5,
                arrayBox.getX() + arrayBox.getWidth(), arrayBox.getY() + arrayBox.getHeight()
        });
    }

    private void refreshValue() {
        double valueHeight = getValueHeight(displayValue);
        valueBox.setY(getPadding().getTop() + getMaxValueHeight() - valueHeight);
        valueBox.setHeight(valueHeight);
    }

    private double getAvailableWidth() {
        return getPrefWidth() - getPadding().getLeft() - getPadding().getRight();
    }

    private double getMaxValueHeight() {
        return arrayBox.getY() - getPadding().getTop() - valueOffset.getValue().doubleValue();
    }

    private double getValueHeight(double value) {
        return getMaxValueHeight() * (value / maxValue);
    }

    public void setStale() {
        valueBox.getStyleClass().remove("active");
        valueBox.getStyleClass().add("stale");
    }

    public void setFresh() {
        refreshValue();
        valueBox.setOpacity(1);
        valueBox.getStyleClass().remove("stale");
        valueBox.getStyleClass().add("active");
    }

    public void queueValue(int value) {
        this.currentValue = value;
        values.add(value);
    }

    public void nextValue() {
        this.displayValue = values.remove();
        setFresh();
    }

    public void setMarker(int marker, boolean state) {
        if (marker == 0) {
            this.primaryMarkerOn = state;
            this.primaryMarker.setOpacity(state ? 1: 0);
        } else if (marker == 1) {
            this.secondaryMarkerOn = state;
            this.secondaryMarker.setOpacity(state ? 1: 0);
        }
    }

    public void moveValueTo(ArrayValueGraph destination) {
        var m = getValueRectangle();
        parent.getChildren().add(m);
        this.setStale();
        destination.queueValue((int)currentValue);

        var t = new TranslateTransition();
        t.setDuration(Duration.millis(1000));
        t.setNode(m);
        t.setByX(destination.getValuePositionX() - (getLayoutX() + valueBox.getX()));
        t.statusProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue == Animation.Status.STOPPED) {
                destination.nextValue();
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
