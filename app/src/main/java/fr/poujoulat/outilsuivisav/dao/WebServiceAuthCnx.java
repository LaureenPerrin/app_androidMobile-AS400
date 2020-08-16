package fr.poujoulat.outilsuivisav.dao;


import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebServiceAuthCnx extends AsyncTask<String, Void, String> {


    @Override protected String doInBackground(String... strings) {
        Log.i("ACOS","Entrée dans le doInBackground de la tâche asynchrone");
        HttpURLConnection httpUrlConnection = null;
        StringBuffer stringBuffer = new StringBuffer();

        try {
            //TODO Mettre votre adresse de serveur contenant le webservice.
            Log.i("ACOS","Création de l'objet URL");
           // exemple :  URL url = new URL("http://192.168.179.1:8080/TrucFournisseur/ress?p="+strings[0]);
            URL url = new URL("http://localhost:****/WSPortal/rest/portal/userframework/***********?login="+strings[0]+"&password="+strings[1]+"&appName="+strings[2]);
            Log.i("ACOS","Création de l'objet HttpURLConnection et envoi de la requête");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            Log.i("ACOS","Récupération de la réponse");
            InputStream in = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            int unCharacter;

            while((unCharacter = isr.read()) != -1) {
                stringBuffer.append((char)unCharacter);
            }

            connection.disconnect();
        } catch(Exception ex) {
            Log.e("ACOS","ERREUR : " + ex.getMessage());
        }
        Log.i("ACOS","Résultat : " + stringBuffer.toString());

        return stringBuffer.toString();
    }

    @Override protected void onPostExecute(String s) {

    }

}










