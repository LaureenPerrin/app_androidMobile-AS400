package fr.poujoulat.outilsuivisav.dao;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class BddCnxAsync extends AsyncTask<Void, Void, Connection> {

    //URL de connexion
    private String url = "jdbc:as400://************";
    //Nom du user
    private String user = "***";
    //Mot de passe de l'utilisateur
    private String password = "****";
    //Objet Connection
    private Connection connect;

    public BddCnxAsync() {
        try {
            Class.forName("com.ibm.as400.access.AS400JDBCDriver");
            connect = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Connection doInBackground(Void... voids) {
        if(connect == null){
            new BddCnxAsync();
        }
        return connect;
    }


}