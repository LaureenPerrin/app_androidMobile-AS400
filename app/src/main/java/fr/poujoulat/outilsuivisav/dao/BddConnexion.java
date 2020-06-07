package fr.poujoulat.outilsuivisav.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BddConnexion {

    //URL de connexion
    private String url = "jdbc:as400://**********";
    //Nom du user
    private String user = "****";
    //Mot de passe de l'utilisateur
    private String passwd = "****";
    //Objet Connection
    private static Connection connect;

    //Constructeur privé
    private BddConnexion(){
        try {
            Class.forName("com.ibm.as400.access.AS400JDBCDriver");
            connect = DriverManager.getConnection(url, user, passwd);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Méthode qui va nous retourner notre instance et la créer si elle n'existe pas
    public static Connection getConnection(){
        if(connect == null){
            new BddConnexion();
        }
        return connect;
    }

  
}
