package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import connexion.DAOAcces;

import model.ModelCase;
import model.ModelPropriete;

public class ControllerPropriete {
    public static ArrayList<ModelPropriete> proprietePlateau() {
        String reqSQL = "SELECT p.*, c.nom_case, c.type_case\n" +
                "FROM polysio.propriete p  \n" +
                "JOIN case_plateau c USING (id_case_plateau)\n" +
                "ORDER BY id_propriete ASC";
        ArrayList<ModelPropriete> listeProprieteBdd = new ArrayList<>();
        try (PreparedStatement pst = DAOAcces.getConnexion().prepareStatement(reqSQL);

             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                {
                    ModelPropriete proprietes =  new ModelPropriete(
                            rs.getInt("id_propriete"),
                            rs.getBoolean("batiment"),
                            rs.getInt("id_case_plateau"),
                            rs.getString("chemin_svg"),
                            rs.getInt("loyer_nue"),
                            rs.getInt("loyer_batiment"),
                            rs.getInt("id_couleur"),
                            rs.getInt("prix"),
                            rs.getString("nom_case"),
                            rs.getString("type_case")

                            //MainApp.cfgPolysio.get("pf.case"+rs.getInt("id_case_plateau"))
                    );
                    listeProprieteBdd.add(proprietes);
                }
            }
            System.out.println("DEBUG SQL : Nombre de cases récupérées = " + listeProprieteBdd.size());
            return listeProprieteBdd;
        }
        catch (SQLException erreur) {
            // Un seul CATCH : erreurs SQL
            System.err.println("Erreur SQL : Liste Cases non récupérée " + erreur.getMessage());
            erreur.printStackTrace();
        }
        return listeProprieteBdd;}}




