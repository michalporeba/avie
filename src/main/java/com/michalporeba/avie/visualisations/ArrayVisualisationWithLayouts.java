package com.michalporeba.avie.visualisations;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ArrayVisualisationWithLayouts<T extends Number>
    extends ArrayVisualisation<T>
{
    private HBox pane;
    private Rectangle[] arrayNodes;

    public Pane getVisualisation() {
        if (pane == null) {

        }

        return pane;
    }

    public void show(T[] data) {
        int l = data.length;
        arrayNodes = new Rectangle[l];
        double w = 600;
        double size = w/l;

        for(int i = 0; i < l; ++i) {
            Rectangle r = new Rectangle();
            r.setX(i*size);
            r.setHeight(40);
            //r.xProperty().bind(pane.widthProperty().divide(l).multiply(i));
            r.setStroke(Color.RED);
            r.setStrokeWidth(3);
            arrayNodes[i] = r;
            pane.getChildren().add(r);
        }
        adjust();
    }

    private void adjust() {
        double size = (pane.getWidth()-20) / arrayNodes.length;
        if(size > 50) size = 50;

        for(int i = 0; i < arrayNodes.length; ++i) {
            arrayNodes[i].setWidth(size - 10);
            arrayNodes[i].setX(10+i*size);
            arrayNodes[i].setY(pane.getHeight()-50);
        }
    }
}
