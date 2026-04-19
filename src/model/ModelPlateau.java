package model;

import View.ViewCase;
import View.ViewPropriete;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

// On utilise StackPane pour pouvoir empiler le background ET le plateau par dessus
public class ModelPlateau extends GridPane {
    private List<ModelCase> listeCases;
    private List<ViewCase> listeViewCases = new ArrayList<>();
    private List<ModelPropriete> listePropriete;
    private List<ViewPropriete> listeViewPropriete = new ArrayList<>();;
    private ArrayList<ModelJoueur> listeJoueurs;

    public ModelPlateau(List<ModelCase> listeCasesP, List<ModelPropriete> listeProprieteP) {
        this.listeCases = listeCasesP;
        this.listePropriete = listeProprieteP;
        /*
        this.prefHeightProperty().bind(this.heightProperty().multiply(0.8));
        this.prefWidthProperty().bind(this.heightProperty().multiply(0.8));
         */

        this.setAlignment(Pos.CENTER);
        this.setGridLinesVisible(false); // Mets à true pour débugger et voir la grille !

        // On force une taille minimale pour le test si le bind échoue
        //this.setMinSize(400, 400);
        //this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        // 0, 0, 0 = Noir | 1.0 = Opacité
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 1.0);");

        // Espacement entre cellule grille (optionnel) :
        /*
        this.plateau.setHgap(2);
        this.plateau.setVgap(2);
        */
        this.initialiserPlateau();
    }

    public List<ModelCase> getListeCases() {
        return listeCases;
    }

    public List<ModelPropriete> getListePropriete() {
        return listePropriete;
    }

    public void initialiserPlateau() {
        // On clear toujours la gridPane pour s'assurer qu'elle est vide
        this.getChildren().clear();
        this.getColumnConstraints().clear();
        this.getRowConstraints().clear();

        // Je crée une boucle pour assigner des positions à chaque case de la liste
        // Important, les cases doivent être Ordonnées dans la liste
        for (int i = 0; i < 11; i++) {
            ColumnConstraints col = new ColumnConstraints();
            RowConstraints row = new RowConstraints();
            if (i == 0 || i == 10) {
                col.setPercentWidth(12.5);
                row.setPercentHeight(12.5);
            } else {
                col.setPercentWidth(8.3);
                row.setPercentHeight(8.3);
            }
            this.getColumnConstraints().add(col);
            this.getRowConstraints().add(row);
        }

        // Inserer controller plateau pour créer le plateau graphique à partir des model cases
        for (ModelCase caseModelCase : this.listeCases) {
            // On ignore la case centrale ici pour ne pas l'ajouter en 1x1 dans la boucle
            if (caseModelCase.getId() == 99 || caseModelCase.getNom().equalsIgnoreCase("centre")) {
                continue;
            }

            ViewCase casePlateau = new ViewCase(caseModelCase);

// FORCE LA CASE À PRENDRE TOUTE LA PLACE DE LA CELLULE
            GridPane.setHgrow(casePlateau, Priority.ALWAYS);
            GridPane.setVgrow(casePlateau, Priority.ALWAYS);

            int colonne = caseModelCase.getPositionX();
            int ligne = caseModelCase.getPositionY();

            // Utilisation directe de add() pour plus de clarté
            this.add(casePlateau, colonne, ligne);
            // ToDo : Création d'une liste de ViewCase pour utiliser pour les popup etc (est ce indispensable ?? )
            listeViewCases.add(casePlateau);
        }

        // ToDo : CaseCentrale est la derniere pour l'instant mais peut etre prendre getName ou Id à la place ?
        // On récupère la case centrale et on lui donne un Span de 9x9 pour remplir le milieu
        ModelCase mCentrale = this.listeCases.getLast();
        ViewCase vueCentrale = new ViewCase(mCentrale);
        this.add(vueCentrale, 1, 1, 9, 9);

        // Constuire les Cartes de proprietees :

        for (ModelPropriete propriete : this.listePropriete) {
            ViewPropriete viewPropriete = new ViewPropriete(propriete);
            listeViewPropriete.add(viewPropriete);
        }
    }

    public List<ViewCase> getListeViewCases() {
        return listeViewCases;
    }
    /*public ViewCase getViewCase(int idP) {
        return listeViewCases.get(idP);
    }*/

    public List<ViewPropriete> getListeViewPropriete() {
        return listeViewPropriete;
    }
    
    

}