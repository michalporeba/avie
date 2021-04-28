package com.michalporeba.avie;

import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Avie extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Avie - Algorithm Visualiser");

        Circle circle = new Circle(150, 150, 50, Color.RED);

        MenuBar menuBar = new MenuBar();
        Pane pane = new Pane(circle);
        VBox root = new VBox(menuBar, pane);

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}