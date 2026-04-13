package model;

public enum ModelJoueurCouleur {
    ROUGE("#FF0000"),
    VERT("#00FF00"),
    BLEU("#0000FF"),
    JAUNE("#FFFF00");

    private final String couleurJoueur;

    // Le constructeur d'une Enum est TOUJOURS privé par défaut
    ModelJoueurCouleur (String couleurJoueurP) {
        this.couleurJoueur = couleurJoueurP;
    }

    public String getCouleurJoueur() {
        return couleurJoueur;
    }
}
