package connexion;

import java.io.*;
import java.util.Properties;

public class OldConfigLoader {
    public static final String ENV_FILE_PATH = "config.properties";
    public static final String POLYSIO_FILE_PATH = "polysio.properties";
    private Properties props = new Properties();
    private String filename;
    private String cheminComplet; // Chemin absolu du fichier

    // Constructeur pour instancier des fichiers :
    public OldConfigLoader(String filenameP) {
        this.filename = filenameP;
        chargerFichier();
    }

    public void chargerFichier() {
        System.out.println("📂 Chargement de " + filename + "...");

        // Trouver le chemin du fichier
        cheminComplet = trouverCheminFichier();

        if (cheminComplet == null) {
            System.err.println("❌ Fichier introuvable : " + filename);
            return;
        }

        File file = new File(cheminComplet); // Ici filename est celui hérité du constructeur
        if (file.exists()) {
            try (InputStream input = new FileInputStream(file)) {
                props.load(input);
                System.out.println("✅ Configuration chargée du fichier : " + filename + "...");
            } catch (IOException erreur) {
                System.err.println("❌ Erreur critique : " + erreur.getMessage());
            }
        }
    }

    private String trouverCheminFichier() {
        String userDir = System.getProperty("user.dir");

        // config.properties est dans resources/ (parallèle à src/)
        if (filename.equals(ENV_FILE_PATH)) {
            String chemin = userDir + "/resources/" + filename;
            if (new File(chemin).exists()) {
                return chemin;
            }
        }

        // polysio.properties est dans src/ (racine)
        if (filename.equals(POLYSIO_FILE_PATH)) {
            String chemin = userDir + "/src/" + filename;
            if (new File(chemin).exists()) {
                return chemin;
            }
        }

        return null;
    }

    public String get(String key) {
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
        if (cheminComplet == null) {
            System.err.println("❌ Impossible de sauvegarder : chemin inconnu");
            return;
        }

        try (OutputStream output = new FileOutputStream(cheminComplet)) {
            props.store(output, "Mise à jour automatique du fichier " + filename);
            System.out.println("💾 Fichier sauvegardé : " + filename);
        } catch (IOException erreur) {
            System.err.println("Erreur sauvegarde : " + filename + " ... " + erreur.getMessage());
        }
    }

    public int getInt(String key) {
        String val = get(key);
        return (val != null) ? Integer.parseInt(val.trim()) : 0;
    }

    public boolean getBoolean(String key) {
        String value = get(key); // Ta méthode get(String key) actuelle
        return Boolean.parseBoolean(value);
    }
}
