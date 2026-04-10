package model;

public enum ModelJoueurRole {
    JOUEUR("Joueur"),
    SPECTATEUR("Observateur");

    private final String role;

    // Le constructeur d'une Enum est TOUJOURS privé par défaut
    ModelJoueurRole(String role) {
        this.role = role;
    }

    public String getLibelle() {
        return role;
    }
}