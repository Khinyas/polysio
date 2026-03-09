package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connexion.DAOAcces;
import model.ModelCase;
import model.ModelUser;
import model.ModelUserRole;
import service.Securite;

public class ControllerCase {
	   public static ArrayList<ModelCase> casePlateau() {
	        String reqSQL = "SELECT * FROM polysio.case_plateau";
	        ArrayList<ModelCase> listeCaseBdd = new ArrayList<>();
	        try (PreparedStatement pst = DAOAcces.getConnexion().prepareStatement(reqSQL)) {
	          
	            try (ResultSet rs = pst.executeQuery()) {
	                while (rs.next()) { 
	                      {
	                         ModelCase casePlateau =  new ModelCase(
	                                rs.getInt("id_case_plateau"),
	                                rs.getString("nom_case"),
	                                rs.getString("type_case"),
	                                rs.getInt("positionX"),
	                                rs.getInt("positionY"),
	                                rs.getInt("prix"),
	                                rs.getInt("id_couleur")
	                    
	                        );
	                         listeCaseBdd.add(casePlateau);
	                    } 
	                     return listeCaseBdd;
	                }
	        } catch (SQLException erreur) {
	            // Un seul CATCH : erreurs SQL
	            System.err.println("Erreur SQL : Liste Cases non récupérée " + erreur.getMessage());
	            erreur.printStackTrace();
	        }
	        return null; 
	    }

	   }}
