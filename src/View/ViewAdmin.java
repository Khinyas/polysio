package View;

import View.composants.BoutonFermerPoPup;
import View.composants.Header;
import connexion.DAOUser;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.MainApp;
import model.ModelUser;
import java.util.ArrayList;

public class ViewAdmin extends ViewTemplate {
    private TableView<ModelUser> tableUsers = new TableView<>();

    public ViewAdmin() {
        // 1. On appelle le constructeur parent avec le chemin de ton image de fond
        super("/images/accueil.png"); // Remplace par ton chemin réel

        // 2. Création du contenu central spécifique à l'admin
        VBox contenuCentral = new VBox(20);
        contenuCentral.setPadding(new Insets(30));
        contenuCentral.setAlignment(Pos.TOP_CENTER);
        // On rend le fond de la VBox légèrement blanc transparent pour lire le tableau sur le background
        contenuCentral.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8); -fx-background-radius: 15;");
        contenuCentral.setMaxWidth(900); // Pour ne pas que le tableau soit trop large
        contenuCentral.setMaxHeight(600);

        Label titre = new Label("PANNEAU D'ADMINISTRATION");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Configuration du TableView
        configurerTableau();

        // Zone des boutons
        HBox zoneActions = new HBox(20);
        zoneActions.setAlignment(Pos.CENTER);

        Button btnRetour = new Button("🏠 Accueil");
        btnRetour.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        btnRetour.setOnAction(e -> MainApp.changerDePage(new View.ViewConnexion())); // Ou ViewAccueil

        Button btnDelete = new Button("❌ Supprimer l'utilisateur");
        btnDelete.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        btnDelete.setOnAction(e -> supprimerSelection());

        zoneActions.getChildren().addAll(btnRetour, btnDelete);

        // Assemblage du contenu
        contenuCentral.getChildren().addAll(titre, tableUsers, zoneActions);

        // 3. On injecte ce contenu dans le BorderPane du Template
        this.setContenuCentral(contenuCentral);

        // Chargement initial des données
        actualiserDonnees();
    }

    private void configurerTableau() {
        TableColumn<ModelUser, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<ModelUser, String> colPseudo = new TableColumn<>("Pseudo");
        colPseudo.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPseudo.setPrefWidth(200);

        TableColumn<ModelUser, String> colRole = new TableColumn<>("Rôle");
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colRole.setPrefWidth(150);

        tableUsers.getColumns().setAll(colId, colPseudo, colRole);
        tableUsers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableUsers.setPlaceholder(new Label("Aucun utilisateur trouvé."));
    }

    private void actualiserDonnees() {
        ArrayList<ModelUser> liste = DAOUser.reqListeTousLesUtilisateurs();
        tableUsers.setItems(FXCollections.observableArrayList(liste));
    }

    private void supprimerSelection() {
        ModelUser selection = tableUsers.getSelectionModel().getSelectedItem();
        if (selection != null) {
            // Empêcher l'admin de se supprimer lui-même par erreur
            if (selection.getId() == MainApp.getUtilisateurConnecte().getId()) {
                System.out.println("Action impossible : Vous ne pouvez pas supprimer votre propre compte.");
                return;
            }

            if (DAOUser.deleteUSer(selection.getId())) {
                tableUsers.getItems().remove(selection);
            }
        }
    }
}