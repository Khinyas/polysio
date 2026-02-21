package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOAcces {
    private static Connection conn = null;


    public DAOAcces() {
        System.out.println(" Initialisation de la connexion " + ConfigLoader.get("db.type") + "..." + ConfigLoader.get("db.name"));
        throw new UnsupportedOperationException("Classe utilitaire");
    }


    public static Connection getConnexion() {
        try {
            if (conn == null || conn.isClosed()) {
                initConnexion();
            }
            return conn;
        } catch (SQLException erreur) {
            System.out.println("Erreur lors de la vérification de la connexion : " + erreur.getMessage());
            erreur.printStackTrace();
        }
        return null;
    }

    public static void initConnexion() {
        // Paramétrage :
        String driver = ConfigLoader.get("db.driver");
        String dbName = ConfigLoader.get("db.name");
        String url = ConfigLoader.get("db.url");
        String login = ConfigLoader.get("db.login");
        String password = ConfigLoader.get("db.password");
        int PORT = ConfigLoader.getInt("db.port");
        String fullUrl = url +
                "?autoReconnect=" + ConfigLoader.get("db.autoReconnect") +
                "&useSSL=" + ConfigLoader.get("db.useSSL") +
                "&serverTimezone=" + ConfigLoader.get("db.serverTimezone") +
                "&allowPublicKeyRetrieval=true";
        try  {
            Class.forName(driver);
            System.out.println("Chargement du Driver : " + driver);
            conn = DriverManager.getConnection(fullUrl , login, password);
            System.out.println("Connecté à la base de données : " + dbName);
            System.out.println("Url base de donnée : " + fullUrl);
        } catch (ClassNotFoundException erreur) {
            System.out.println("Driver Non Chargé (not found)" + erreur.getMessage());
            erreur.printStackTrace();
            throw new RuntimeException(erreur);
        } catch (SQLException erreur) {
            System.out.println("Erreur SQL, Constructor Fail" + erreur.getMessage());
            erreur.printStackTrace();
        }
    }

    public static void closeConnexion() {
        try {
            conn.close();
            System.out.println("🔌 Connexion base de données fermée.");
        } catch (SQLException erreur) {
            System.out.println("Erreur SQL, Constructor Fail" + erreur.getMessage());
            erreur.printStackTrace();
        }
    }

    public static void resetBaseDeDonnees() {
        // Sécurité : Si on n'est pas en mode debug, on sort direct !
        if (!ConfigLoader.getBoolean("app.debug")) {
            System.out.println("⚠️ Reset annulé : Mode Debug désactivé.");
            return;
        }
        String[] queries = {
                "SET FOREIGN_KEY_CHECKS = 0", // Désactive les contraintes pour pouvoir vider
                "TRUNCATE TABLE proprietes",
                "TRUNCATE TABLE polysio",
                "SET FOREIGN_KEY_CHECKS = 1"  // Réactive les contraintes
        };
        Connection conn = getConnexion();
        try (Statement statement = conn.createStatement()) {
            for (String sql : queries) {
                statement.executeUpdate(sql);
            }
            System.out.println("♻️ Base de données réinitialisée.");
        } catch (SQLException erreur) {
            erreur.printStackTrace();
        }
    }
}

