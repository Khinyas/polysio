package service;

import connexion.ConfigLoader;
import main.MainApp;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Securite {
    public static String hacherPassword(String passwordClairP) {
        try {
            // On utilise l'algorithme SHA-256 natif de Java
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String passwordWithPrivateKey = passwordClairP + MainApp.cfgApp.get("db.privateKey");
            byte[] hash = digest.digest(passwordClairP.getBytes(StandardCharsets.UTF_8));

            // On convertit le résultat (octets) en texte lisible (Base64) pour la BDD
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException erreur) {
            throw new RuntimeException("Erreur de hachage", erreur);
        }
    }

    public static Boolean verifyPassword(String passwordSaisi, String passwordHashedP) {
// Avec SHA-256, on hache la saisie et on compare les deux chaînes de texte
        String saisieHachee = hacherPassword(passwordSaisi);
        return saisieHachee.equals(passwordHashedP);
    }
}
