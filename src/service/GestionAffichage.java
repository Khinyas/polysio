package service;

import javafx.scene.control.Alert;

public class GestionAffichage {


    // Methode pour Afficher des Alertes
    public static void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
