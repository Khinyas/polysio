package main;

import View.ViewConnexion;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    public static Stage mainStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        mainStage.setTitle("POLYSIO");
        ViewConnexion connexion = new ViewConnexion();
        Scene scene = new Scene(connexion, 800, 800);
        mainStage.setScene(scene);
        mainStage.show();
    }
    // LA MÉTHODE MAGIQUE
    public static void changerDePage(StackPane nouvellePage) {
        // On demande à la scène actuelle de jeter l'ancien contenu
        // et de mettre la nouvelle vue à la place
        mainStage.getScene().setRoot(nouvellePage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
