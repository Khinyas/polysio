package View;

import controller.ControllerAdmin;
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
import model.ModelUserRole;
import java.util.ArrayList;

public class ViewAdmin extends ViewTemplate {
    private TableView<ModelUser> tableUsers = new TableView<>();

    public ViewAdmin() {
        super("/images/accueil.png"); 

        // --- CONTENEUR CENTRAL ---
        VBox contenuCentral = new VBox(20);
        contenuCentral.setPadding(new Insets(30));
        contenuCentral.setAlignment(Pos.TOP_CENTER);
        contenuCentral.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-background-radius: 15;");
        contenuCentral.setMaxWidth(900);
        contenuCentral.setMaxHeight(600);

        Label titre = new Label("PANNEAU D'ADMINISTRATION");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        configurerTableau();

        // --- ZONE DE BOUTONS ---
        HBox zoneActions = new HBox(15);
        zoneActions.setAlignment(Pos.CENTER);

        // Bouton Accueil
        Button btnRetour = new Button("🏠 Accueil");
        btnRetour.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-weight: bold;");
        btnRetour.setOnAction(e -> MainApp.changerDePage(new View.ViewConnexion()));

        // Bouton Modifier
        Button btnEdit = new Button("📝 Modifier");
        btnEdit.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold;");
        btnEdit.setOnAction(e -> ouvrirPopupModification());

        // Bouton Supprimer
        Button btnDelete = new Button("❌ Supprimer");
        btnDelete.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        btnDelete.setOnAction(e -> {
            ModelUser selection = tableUsers.getSelectionModel().getSelectedItem();
            ControllerAdmin.supprimerUtilisateur(selection, tableUsers);
        });

        zoneActions.getChildren().addAll(btnRetour, btnEdit, btnDelete);

        // Assemblage
        contenuCentral.getChildren().addAll(titre, tableUsers, zoneActions);
        this.setContenuCentral(contenuCentral);

        actualiserDonnees();
    }

    private void configurerTableau() {
        TableColumn<ModelUser, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<ModelUser, String> colPseudo = new TableColumn<>("Pseudo");
        colPseudo.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<ModelUser, String> colRole = new TableColumn<>("Rôle");
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        tableUsers.getColumns().setAll(colId, colPseudo, colRole);
        tableUsers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void actualiserDonnees() {
        ArrayList<ModelUser> liste = DAOUser.reqListeTousLesUtilisateurs();
        tableUsers.setItems(FXCollections.observableArrayList(liste));
    }

    private void ouvrirPopupModification() {
        ModelUser selection = tableUsers.getSelectionModel().getSelectedItem();
        if (selection == null) return;

        // Création d'une petite fenêtre de dialogue (Alert ou Stage)
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modification Utilisateur");
        dialog.setHeaderText("Modifier les informations de : " + selection.getUsername());

        // Champs du formulaire
        VBox vb = new VBox(10);
        TextField fieldPseudo = new TextField(selection.getUsername());
        TextField fieldEmail = new TextField(selection.getEmail());
        ComboBox<ModelUserRole> comboRole = new ComboBox<>(FXCollections.observableArrayList(ModelUserRole.values()));
        comboRole.setValue(selection.getRole());

        vb.getChildren().addAll(new Label("Pseudo :"), fieldPseudo, new Label("Email :"), fieldEmail, new Label("Rôle :"), comboRole);
        dialog.getDialogPane().setContent(vb);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                ControllerAdmin.modifierUtilisateur(selection, fieldPseudo.getText(), fieldEmail.getText(), comboRole.getValue(), tableUsers);
            }
        });
    }
}
