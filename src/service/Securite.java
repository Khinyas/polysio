package service;

import connexion.ConfigLoader;
import main.MainApp;
import org.mindrot.jbcrypt.BCrypt;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Securite {
    public static String hacherPassword(String passwordClairP) {
        String passwordWithPrivateKey = passwordClairP + MainApp.cfgApp.get("db.privateKey");
        return BCrypt.hashpw(passwordWithPrivateKey, BCrypt.gensalt(12));
    }

    public static Boolean verifyPassword(String passwordSaisi, String passwordHashedP) {
        // BCrypt extrait lui-même le sel du hash stocké pour comparer
        return BCrypt.checkpw(passwordSaisi, passwordHashedP);
    }
}
