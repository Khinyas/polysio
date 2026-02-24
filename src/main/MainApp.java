package main;

import View.ViewConnexion;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.ModelUser;

public class MainApp extends Application {
    public static Stage mainStage;
    private static ModelUser utilisateurConnecte = null;
    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        mainStage.setTitle("POLYSIO");
        ViewConnexion connexion = new ViewConnexion();
        Scene scene = new Scene(connexion);
        mainStage.setScene(scene);
        mainStage.show();
    }
    // TODO: Afficher un label rouge ou une Alert JavaFX ici
    // TODO: Afficher un label rouge ou une Alert JavaFX ici
    // CHANGEMENT DE PAGE
    public static void changerDePage(javafx.scene.Parent nouvellePage) {
        // On demande à la scène actuelle de jeter l'ancien contenu
        // et de mettre la nouvelle vue à la place
        mainStage.getScene().setRoot(nouvellePage);
    }
    public static void setUtilisateurConnecte(ModelUser user) {
        utilisateurConnecte = user;
    }

    public static ModelUser getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
