package connexion;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static Properties props = new Properties();

    static {
        try  {
            System.out.println("📂 Chargement de config.properties...");
            // On cherche le fichier dans le dossier resources marqué
            InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties");
            if (input == null) {
                // Sécurité : on tente avec un slash si le premier échoue
                input = ConfigLoader.class.getResourceAsStream("/config.properties");
            }
            if (input == null) {
                throw new java.io.IOException("❌ Fichier config.properties introuvable dans le dossier resources !");
            }
            //SUCCESS
            props.load(input);
            input.close();
            System.out.println("✅ Configuration chargée !");
            } catch (java.io.IOException erreur) {
            System.err.println("❌ Erreur critique : " + erreur.getMessage());
            // On ne quitte pas forcément, mais on prévient que les variables seront nulles
            }
    }
    public static String get(String key) {
        return props.getProperty(key);
    }

    public static int getInt(String key) {
        String val = get(key);
        return (val != null) ? Integer.parseInt(val.trim()) : 0;
    }

    public static boolean getBoolean(String key) {
        String value = get(key); // Ta méthode get(String key) actuelle
        return Boolean.parseBoolean(value);
    }
}

