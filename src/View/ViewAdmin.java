package View;

import java.awt.Insets;

import connexion.DAOUser;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.ModelUser;

public class ViewAdmin extends VBox {
    private TableView<ModelUser> tableUsers = new TableView<>();

    @SuppressWarnings("unchecked")
	public ViewAdmin() {
        this.setSpacing(20);
        

        // 1. Définition des colonnes
        TableColumn<ModelUser, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ModelUser, String> colPseudo = new TableColumn<>("Pseudo");
        colPseudo.setCellValueFactory(new PropertyValueFactory<>("pseudo"));

        TableColumn<ModelUser, String> colRole = new TableColumn<>("Rôle");
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        tableUsers.getColumns().addAll(colId, colPseudo, colRole);

        // 2. Chargement des données (Appel au DAO)
        // Il faudra créer une méthode DAOUser.reqListeUtilisateurs()
        // tableUsers.setItems(FXCollections.observableArrayList(DAOUser.reqListeUtilisateurs()));

        // 3. Bouton Supprimer
        Button btnDelete = new Button("Supprimer l'utilisateur sélectionné");
        btnDelete.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        btnDelete.setOnAction(e -> supprimerSelection());

        this.getChildren().addAll(new Label("Gestion des Utilisateurs"), tableUsers, btnDelete);
    }

    private void supprimerSelection() {
        ModelUser selection = tableUsers.getSelectionModel().getSelectedItem();
        if (selection != null) {
            // Appel à ton DAO existant
            if (DAOUser.deleteUSer(selection.getId())) {
                tableUsers.getItems().remove(selection);
                System.out.println("Utilisateur supprimé !");
            }
        }
    }
}