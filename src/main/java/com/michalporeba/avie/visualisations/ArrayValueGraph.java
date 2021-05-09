package com.michalporeba.avie.visualisations;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

class ArrayValueGraph {
    private final Pane parent;
    private final Pane root;
    private Rectangle arrayBox = new Rectangle();
    private Rectangle valueBox = new Rectangle();
    private Text label;
    private double value = 0;
    private double maxValue = 0;
    private final double VALUE_MARGIN = 2;

    public ArrayValueGraph(Pane parent, String name, int maxValue) {
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

    public void setX(double x) {
        root.setLayoutX(x);
    }

    public void setY(double y) {
        root.setLayoutY(y);
    }

    public void setHeight(double height) {
        root.setPrefHeight(height);
    }

    public void setWidth(double width) {
        root.setPrefWidth(width);
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
        return this.root.getLayoutX() + this.valueBox.getX();
    }

    public Rectangle getValueRectangle() {
        var rectangle = new Rectangle(
                root.getLayoutX() + valueBox.getX()
                , root.getLayoutY() + valueBox.getY()
                , valueBox.getWidth(), valueBox.getHeight()
        );
        rectangle.getStyleClass().add("moving-value");
        return rectangle;
    }

    public void refresh() {
        arrayBox.setX(VALUE_MARGIN);
        arrayBox.setY(root.getPrefHeight() - root.getPrefWidth() - 2 * VALUE_MARGIN);
        arrayBox.setWidth(root.getPrefWidth() - 2 * VALUE_MARGIN);
        arrayBox.setHeight(root.getPrefWidth());

        label.setLayoutX(arrayBox.getX() + arrayBox.getWidth() / 2 - label.getLayoutBounds().getWidth() / 2);
        label.setLayoutY(arrayBox.getY() + arrayBox.getHeight() / 2);

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

    public void moveValueTo(ArrayValueGraph destination) {
        var m = getValueRectangle();
        parent.getChildren().add(m);
        setStale();

        var t = new TranslateTransition();
        t.setDuration(Duration.millis(1000));
        t.setNode(m);
        this.setStale();
        t.setByX(destination.getValuePositionX() - (root.getLayoutX() + valueBox.getX()));
        t.statusProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Animation.Status.STOPPED) {
                destination.setValue((int) value);
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
