package com.example.javaproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main class is the entry point of the JavaFX application.
 */
public class Main extends Application {
        private static String filePathLevelGrid =  "src/ReadLevelGridCSV.csv";
        private static String filePathHighScore = "src/HighScoreCSV.csv";

    /**
     * Starts the JavaFX application.
     *
     * @param stage The primary stage for the application.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));
            Parent root = loader.load();
            Controller controller = loader.getController();
            controller.setStage(stage);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("welcome.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method, which launches the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
