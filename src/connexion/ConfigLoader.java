package connexion;

import java.io.*;
import java.util.Properties;

public class ConfigLoader {
    public static final String ENV_FILE_PATH = "resources/config.properties";
    public static final String POLYSIO_FILE_PATH = "src/polysio.properties";
    private Properties props = new Properties();
    private String filename;

    // Constructeur pour instancier des fichiers :
    public ConfigLoader(String filenameP) {
        this.filename = filenameP;

        // Essaie 1 : chemin relatif classique (fonctionne dans IntelliJ)
        File file = new File(filenameP);

        // Essaie 2 : juste le nom du fichier à côté du JAR (fonctionne en production)
        if (!file.exists()) {
            String nomFichier = new File(filenameP).getName();
            file = new File(System.getProperty("user.dir") + File.separator + nomFichier);
        }

        if (file.exists()) {
            try (InputStream input = new FileInputStream(file)) {
                props.load(input);
                System.out.println("✅ Configuration chargée du fichier : " + file.getAbsolutePath() + "...");
            } catch (IOException erreur) {
                System.err.println("❌ Erreur critique : " + erreur.getMessage());
            }
        } else {
            System.err.println("❌ Fichier introuvable : " + file.getAbsolutePath());
        }
    }


    public String get(String key) {
        // ToDo : Attention à supprimer
        System.out.println("Valeur trouvée : " + "trouvée");
        return props.getProperty(key);
    }

    public String getWithDefaultValue(String key, String defaultvalue) {
        return props.getProperty(key, defaultvalue);
    }

    public void set(String key, String value) {
        props.setProperty(key, value);
        sauvegarderFichier();
    }

    public void setInMemory(String key, String value) {
        props.setProperty(key, value);
        System.out.println("📝 Modifié en mémoire (RAM) : " + key + " = " + value);
    }

    public void sauvegarderFichier() {
        if (filename == null) {
            System.err.println("❌ Impossible de sauvegarder : chemin inconnu");
            return;
        }

        try (OutputStream output = new FileOutputStream(
                System.getProperty("user.dir") + File.separator + new File(filename).getName())) {
            props.store(output, "Mise à jour automatique du fichier " + filename);
            System.out.println("💾 Fichier sauvegardé : " + filename);
        } catch (IOException erreur) {
            System.err.println("Erreur sauvegarde : " + filename + " ... " + erreur.getMessage());
        }
    }

    public int getInt(String key) {
        String val = get(key);
        // ToDo : Attention à supprimer
        System.out.println("Valeur trouvée : " + val);
        return (val != null) ? Integer.parseInt(val.trim()) : 0;
    }

    public boolean getBoolean(String key) {
        String value = get(key); // Ta méthode get(String key) actuelle
        return Boolean.parseBoolean(value);
    }
}