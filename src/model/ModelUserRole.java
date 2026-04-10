package model;

public enum ModelUserRole {
    ADMIN("Administrateur"),
    UTILISATEUR("Utilisateur");

    private final String role;

    // Le constructeur d'une Enum est TOUJOURS privé par défaut
    ModelUserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}

/**
 * Utilisation Création :
 *
 * // Version pour un admin
 * Utilisateur chef = new Utilisateur("Keno", Role.ADMIN);
 *
 * // Version pour un joueur
 * Utilisateur joueur1 = new Utilisateur("Invité123", Role.JOUEUR);
 */


/**
 * Utilisation Acces :
 *
 * if (user.getRole() == Role.ADMIN) {
 *     System.out.println("Accès aux outils de triche autorisé !");
 * }
 */


/**
 * Utilisation Courante :
 *
 * public void afficherInfos() {
 *     System.out.println("Pseudo : " + this.pseudo);
 *
 *     // On accède aux méthodes de l'Enum via la variable role
 *     System.out.println("Rôle : " + this.role.getLibelle());
 *     System.out.println("Priorité : " + this.role.getNiveauPriorite());
 * }
 */

/**
 * Utilisation avec la base de Données :
 *
 * Le lien avec ta Base de Données
 * Avec DAOAcces. En base de données, tu stockeras probablement le rôle sous forme de String ("ADMIN") ou d'entier (1).
 *
 * Pour transformer le String de la DB en Enum :
 * Role r = Role.valueOf("ADMIN");
 *
 * Pour transformer l'Enum en String pour la DB :
 * String s = Role.ADMIN.name();
 */