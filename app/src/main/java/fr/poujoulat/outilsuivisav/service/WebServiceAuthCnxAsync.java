package fr.poujoulat.outilsuivisav.service;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import fr.poujoulat.outilsuivisav.activity.ConnexionOutilSavActivity;
import fr.poujoulat.outilsuivisav.R;

//classe de connexion au web service REST d'authentification des utilisateurs :
public class WebServiceAuthCnxAsync extends AsyncTask<String, Void, JSONObject> {

    @SuppressLint("StaticFieldLeak")
    private volatile ConnexionOutilSavActivity connexionOutilSavActivity;


    public WebServiceAuthCnxAsync(ConnexionOutilSavActivity activite) {
            this.connexionOutilSavActivity = activite;
        }

    @Override
    protected JSONObject doInBackground(String... strings) {
        java.net.URL url = null;
        HttpURLConnection urlConnection = null;
        String result = null;
        JSONObject userAuthentifie = null;
        try {

            //tableau de paramètres :
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("login", strings[0]);
            params.put("password", strings[1]);
            params.put("appName", "*********");

            //créé une chaine avec les paramètres :
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            //récupération de l'url de connexion :
            String valeur = this.connexionOutilSavActivity.getResources().getString(R.string.url_connexion_webservice);
            url = new URL(valeur);
            urlConnection = (HttpURLConnection) url.openConnection(); // Open
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            urlConnection.setDoOutput(true);
            urlConnection.getOutputStream().write(postDataBytes);
            Reader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            InputStreamReader r = new InputStreamReader(urlConnection.getInputStream(), "UTF-8");

            //lecture du résultat récupéré via le web service :
            result = readStream(r);

            userAuthentifie = new JSONObject(result);

        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return userAuthentifie;
    }


    //permet de lire le résultat récupéré :
    private String readStream(InputStreamReader is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(is);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

}