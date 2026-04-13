package View.composants;
import View.ViewAdmin;
import controller.ControllerProfil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import main.MainApp;
import model.ModelUser;
import model.ModelUserRole;


public class Header extends HBox {
    private BoutonAcceuil boutonAcceuil;
    private BoutonInscription boutonInscription;
    private BoutonConnexion boutonConnexion;
    private BoutonDes boutonDes;
    private BoutonDeconnexion boutonDeconnexion;
    

    public Header() {
        this.setPadding(new Insets(10));
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-background-color: transparent;");

        // Bouton Accueil : Toujours présent
        this.boutonAcceuil = new BoutonAcceuil();
        this.getChildren().add(boutonAcceuil);
        
     
        

       
        ModelUser user = MainApp.getUtilisateurConnecte();
        if (user == null) {


            // --- CAS : UTILISATEUR NON CONNECTÉ ---

            // Bouton Inscription
            this.boutonInscription = new BoutonInscription();
            //Bouton Connexion
            this.boutonConnexion = new BoutonConnexion();



            // Remplissage Conteneur (On ajoute uniquement les boutons de navigation standard)
            this.getChildren().addAll(boutonInscription, boutonConnexion);
            
        }
          
        
        else {
            // --- CAS : UTILISATEUR CONNECTÉ ---
        	
        	 
            if (MainApp.getUtilisateurConnecte() != null && MainApp.getUtilisateurConnecte().getRole() == ModelUserRole.ADMIN) {
                Button btnAdmin = new Button("Administration");
                btnAdmin.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
                
                btnAdmin.setOnAction(event -> {
                    // Redirection vers ta nouvelle vue admin
                    MainApp.changerDePage(new ViewAdmin()); 
                });
                this.getChildren().add(1, btnAdmin); 
            
                ;}

            // Bouton Deconnexion
            this.boutonDeconnexion = new BoutonDeconnexion();
            this.getChildren().add(boutonDeconnexion);

            // --- CORRECTION ICI ---
            // Au lieu de setHgrow sur le bouton (qui ne marcherait pas bien),
            // on utilise un Spacer (Region) qui va pousser tout le reste à droite.
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            this.getChildren().add(spacer);
            HBox zoneProfil = creerZoneProfil();
            this.getChildren().add(zoneProfil); }}

            // Note : L'alignement reste CENTER_LEFT, le spacer s'occupe de l'écartement
        
    

    // Le Getter pour pouvoir rajouter un EVENT depuis un controller (afficher page Accueil)
    public BoutonAcceuil getBoutonAcceuil() {
        return boutonAcceuil;
    }
    public BoutonDeconnexion BoutonDeconnexion() {
        return boutonDeconnexion;
    }

    // Getters pour les autres boutons (Retourneront null si l'utilisateur est connecté)
    public BoutonInscription getBoutonInscription() { return boutonInscription; }
    public BoutonConnexion getBoutonConnexion() { return boutonConnexion; }
    
    


    public HBox creerZoneProfil() {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_RIGHT);
        // box.setPadding(new Insets(0, 0, 0, 500)); // Suppression du padding fixe, le Spacer fait le travail !
        /**
         * Utilisateur Existant :
         *
         */
        if (MainApp.getUtilisateurConnecte() != null) {
            // AVATAR UTILISATEUR
            // Avatar (Petit rond ou image qui s'affiche quand l'utilisateur est connecté)
            ImageView avatar = new ImageView(new Image(MainApp.getUtilisateurConnecte().getProfilepicture()));
            avatar.setFitHeight(120);
            avatar.setFitWidth(90);

            // Nom de l'utilisateur
            javafx.scene.control.Label lblName = new javafx.scene.control.Label(MainApp.getUtilisateurConnecte().getUsername());
            lblName.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

            box.getChildren().addAll(lblName, avatar);
            box.setCursor(javafx.scene.Cursor.HAND);
            box.setOnMouseClicked(event -> // C'est ICI qu'on appelle le contrôleur au moment du clic !
                    new ControllerProfil());
            System.out.println("Ouverture Profil User " + MainApp.getUtilisateurConnecte().getUsername());

        }
        
        else {
            /**
             * Utilisateur Non Connecté
             */
            ImageView avatar = new ImageView(new Image("/images/avatars/avatar_logout.jpg"));
            avatar.setFitHeight(120);
            avatar.setFitWidth(90);

            // Nom de l'utilisateur
            javafx.scene.control.Label lblName = new javafx.scene.control.Label("Déconnecté");
            lblName.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

            box.getChildren().addAll(lblName, avatar);
            box.setCursor(javafx.scene.Cursor.HAND);
            box.setOnMouseClicked(event -> System.out.println("Utilisateur non connecté " ));
        }

        return box; // Ajout du point-virgule manquant ici
    }
}


/**
 * Ici la partie importante, c est les attributs de ma class HEADER :
 *     private BoutonAcceuil boutonAcceuil;
 *     private BoutonInscription boutonInscription;
 *     private BoutonConnexion boutonConnexion;
 *
 *     Je les instancie avec le moule que j'ai fait qui EXTENDS BUTTON,
 */

/**
 * NOTE FINALE :
 * Ma Méthode n'est pas forcement la meilleur ni la plus adapté à un besoin mais on peut facilement Extends differement.
 *
 * Du coups quand je change de vue cela donne :
 *
 *             // 2 On crée la nouvelle vue (Accueil) avec le profil
 *             ViewAccueil vueAccueil = new ViewAccueil(profilUtilisateur);
 *             MainApp.changerDePage(vueAccueil);
 *
 *             Je crée une vue et me sert de ma méthode Static accessible partout
 */