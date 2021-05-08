package com.michalporeba.avie;

import com.michalporeba.avie.algorithms.InsertionSort;
import com.michalporeba.avie.visualisations.ArrayVisualisation;
import com.michalporeba.avie.visualisations.ArrayVisualisationWithGraph;
import com.michalporeba.avie.visualisations.ArrayVisualisationWithLayouts;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Avie extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Avie - Algorithm Visualiser");

        InsertionSort algorithm = new InsertionSort();
        algorithm.setup(new int[]{3, 8, 2, 7, 1, 4, 5, 9});

        /*
        System.out.println(Arrays.toString(algorithm.getData()));
        for(Operation s : algorithm) {
            System.out.println(s);
        }
        System.out.println(Arrays.toString(algorithm.getData()));
        */

        MenuBar menuBar = new MenuBar();
        Pane pane = new VBox();

        BorderPane root = new BorderPane();
        root.setCenter(pane);

        var css = this.getClass().getResource("Avie.css").toExternalForm();
        root.getStyleClass().add("avie");
        root.getStylesheets().add(css);


        var v1 = new ArrayVisualisationWithGraph();
        //var v2 = new ArrayVisualisationWithLayouts();

        algorithm.attachTo(v1);

        v1.setPrefHeight(400);
        v1.prefWidthProperty().bind(pane.widthProperty());
        //v2.prefWidthProperty().bind(pane.widthProperty());

        pane.getChildren().add(v1);
        //pane.getChildren().add(v2);

        primaryStage.setScene(new Scene(root, 200, 400));
        primaryStage.show();
    }
}