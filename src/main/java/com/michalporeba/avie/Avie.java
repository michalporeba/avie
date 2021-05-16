package com.michalporeba.avie;

import com.michalporeba.avie.algorithms.ArrayAlgorithm;
import com.michalporeba.avie.algorithms.BubbleSort;
import com.michalporeba.avie.algorithms.InsertionSort;
import com.michalporeba.avie.visualisations.StandardArrayVisualisation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Avie extends Application {
    private Timeline timeline;
    private final List<ArrayAlgorithm> algorithms = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Avie - Algorithm Visualiser");


        MenuBar menuBar = new MenuBar();
        Pane pane = new VBox();

        BorderPane root = new BorderPane();
        root.setCenter(pane);

        var css = this.getClass().getResource("Avie.css").toExternalForm();
        root.getStyleClass().add("avie");
        root.getStylesheets().add(css);

        algorithms.add(new BubbleSort());
        algorithms.add(new InsertionSort());

        for(var algorithm : algorithms) {
            algorithm.setup(new int[]{3, 8, 2, 7, 1, 4, 5, 9, 6});
            var v = new StandardArrayVisualisation();
            v.setPrefHeight(500);
            pane.getChildren().add(v);
            algorithm.attachTo(v);
        }
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();

        var timeline = new Timeline();

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200), e -> {
            var stillWorking = false;
            for(var algorithm : algorithms) {
                if (!algorithm.isComplete()) {
                    algorithm.progress();
                    stillWorking = true;
                }
            }
            if (!stillWorking) {
                timeline.setOnFinished(null);
            }
        }));

        timeline.setCycleCount(1);
        timeline.setOnFinished(e -> timeline.play());
        timeline.play();
    }
}