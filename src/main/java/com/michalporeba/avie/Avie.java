package com.michalporeba.avie;

import com.michalporeba.avie.algorithms.Algorithm;
import com.michalporeba.avie.algorithms.InsertionSort;
import com.michalporeba.avie.visualisations.StandardArrayVisualisation;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Avie extends Application {
    private Timeline timeline;
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


        var v1 = new StandardArrayVisualisation();
        var v2 = new StandardArrayVisualisation();

        algorithm.attachTo(v1);
        algorithm.attachTo(v2);

        v1.setPrefHeight(500);
        pane.getChildren().add(v1);
        v2.setPrefHeight(500);
        pane.getChildren().add(v2);

        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();

        var timeline = new Timeline(
            new KeyFrame(Duration.millis(200), e -> progress(algorithm))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void progress(Algorithm algorithm) {
        System.out.println("*");
        if (algorithm.isComplete()) {
            timeline.setCycleCount(0);
            timeline.stop();
            System.out.println("stopping");
            return;
        }
        algorithm.progress();
    }
}