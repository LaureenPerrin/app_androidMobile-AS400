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
            URL url = new URL("http://localhost:****/*****/rest/portal/userframework/*******?login="+strings[0]+"&password="+strings[1]+"&appName="+strings[2]);
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




/*public class WebServiceAuthCnx  {

    public WebServiceAuthCnx() {
    }


    @Override
        protected JSONObject doInBackground(String... strings) {

            URL url = null;
            HttpURLConnection urlConnection = null;
            String result = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection(); // Open
                InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // Stream

                result = readStream(in); // Read stream
            }
            catch (MalformedURLException e) { e.printStackTrace(); }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }

            JSONObject json = null;
            try {
                json = new JSONObject(result);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //metre condition pour le retour :

            //on retourne un commercial :
            return json;

            //on retourne une erreur :

        }


        private String readStream(InputStream is) throws IOException {
            StringBuilder sb = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
            for (String line = r.readLine(); line != null; line =r.readLine()){
                sb.append(line);
            }
            is.close();
            return sb.toString();
        }

        //on li un objet commercial au résultat récupéré :
        protected void onPostExecute(JSONObject objetRecup) {

            GererCommercial commercial = new GererCommercial();
            try {
                //on attribu les données récupéré à un objet GererCommercial :
                commercial.setId(objetRecup.getInt("it"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }*/





