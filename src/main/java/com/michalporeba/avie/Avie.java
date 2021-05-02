package com.michalporeba.avie;

import com.michalporeba.avie.algorithms.InsertionSort;
import com.michalporeba.avie.operations.Operation;
import com.michalporeba.avie.visualisations.ArrayVisualisation;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Arrays;

public class Avie extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Avie - Algorithm Visualiser");

        InsertionSort algorithm = new InsertionSort();
        algorithm.setup(new Integer[]{3, 8, 2, 7, 1, 4, 5, 9});

        /*
        System.out.println(Arrays.toString(algorithm.getData()));
        for(Operation s : algorithm) {
            System.out.println(s);
        }
        System.out.println(Arrays.toString(algorithm.getData()));
        */

        MenuBar menuBar = new MenuBar();
        Pane pane = new AnchorPane();
        pane.maxHeight(Double.MAX_VALUE);
        BorderPane root = new BorderPane();
        root.setCenter(pane);
        root.setStyle("-fx-border-color: red");

        ArrayVisualisation<Integer> v = new ArrayVisualisation<>(pane);
        v.show(algorithm.getData());

        primaryStage.setScene(new Scene(root, 200, 600));
        primaryStage.show();
    }
}