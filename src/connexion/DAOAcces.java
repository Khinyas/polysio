package connexion;

import main.MainApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOAcces {
    private static Connection conn = null;


    public DAOAcces() {
        System.out.println(" Initialisation de la connexion " + MainApp.cfgApp.get("db.type") + "..." + MainApp.cfgApp.get("db.name"));
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
        String driver = MainApp.cfgApp.get("db.driver");
        String dbName = MainApp.cfgApp.get("db.name");
        String url = MainApp.cfgApp.get("db.url");
        String login = MainApp.cfgApp.get("db.login");
        String password = MainApp.cfgApp.get("db.password");
        int PORT = MainApp.cfgApp.getInt("db.port");
        String fullUrl = url +
                "?autoReconnect=" + MainApp.cfgApp.get("db.autoReconnect") +
                "&useSSL=" + MainApp.cfgApp.get("db.useSSL") +
                "&serverTimezone=" + MainApp.cfgApp.get("db.serverTimezone") +
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
}

