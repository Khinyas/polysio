/*package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOAccesOld {
    private static DAOAccesOld instance = null;
    private Connection conn;


    public DAOAccesOld() {
        System.out.println(" Initialisation de la connexion " + ConfigLoader.get("db.type") + "..." + ConfigLoader.get("db.name"));
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
            this.conn = DriverManager.getConnection(fullUrl , login, password);
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

    public static DAOAccesOld getInstance() throws SQLException {
        if(instance == null) {
            try {
                instance = new DAOAccesOld();
            } catch (Exception erreur) {
                System.out.println("Erreur SQL, Instance Fail" + erreur.getMessage());
                erreur.printStackTrace();
                throw new RuntimeException("Impossible de créer la connexion", erreur);
            }
        } return instance;
    }

    public Connection getConnexion() {
        return conn;
    }

    public void closeConnexion() {
        try {
            this.conn.close();
        } catch (SQLException erreur) {
            System.out.println("Erreur SQL, Constructor Fail" + erreur.getMessage());
            erreur.printStackTrace();
        }
    }
}
*/