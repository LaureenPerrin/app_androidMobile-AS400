package fr.poujoulat.outilsuivisav.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import fr.poujoulat.outilsuivisav.bo.GererCommercial;

public class GererCommercialDao {

    private static final String SELECT_ALL = "select coidus as \"id_commercial\", cousrn as \"Identifiants\", coumdp as \"Mot de passe\" from flv00tst.cosavpf";

    public GererCommercialDao() {
    }


    //Récupérer la liste de tous les commerciaux :
    public ArrayList listeCommerciauxTotal() throws Exception {
        ArrayList listeCommerciaux = new ArrayList();


        try {
           Connection cnx = BddConnexion.getConnection();

            try {
                PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
                ResultSet rs = pstmt.executeQuery();

                while(rs.next()) {
                    GererCommercial commercial = new GererCommercial();
                    commercial.setId(rs.getInt("id_commercial"));
                    commercial.setIdentifiant(rs.getString("Identifiants"));
                    commercial.setMotDePasse(rs.getString("Mot de passe"));

                    listeCommerciaux.add(commercial);
                }

                pstmt.close();
                rs.close();

            } finally {
                if (cnx != null) {

                    cnx.close();
                }

            }



        } catch (Exception e) {
            e.printStackTrace();

        }
        return listeCommerciaux;
    }

}
