package fr.poujoulat.outilsuivisav.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import fr.poujoulat.outilsuivisav.R;
import fr.poujoulat.outilsuivisav.bo.GererCommercial;
import fr.poujoulat.outilsuivisav.service.WebServiceAuthCnxAsync;


public class ConnexionOutilSavActivity extends AppCompatActivity {

    private static final String PREFS = "PREFS";
    private static final String PREFS_IDENTIFIANT = "PREFS_IDENTIFIANT";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion_outil_sav);
        Log.d("co", "fonction connexion va etre appellée");
        //récuépérer l'identifiant de connexion :
        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        if( sharedPreferences.contains(PREFS_IDENTIFIANT)){
        /*    String identifiantConnexionRecupere = sharedPreferences.getString("idenitifiantEnregistre",null);
            identifiantSaisi.setText(identifiantConnexionRecupere);
            Log.d("id", identifiantSaisi.toString());*/
            String identifiantConnexionRecupere = sharedPreferences.getString(PREFS_IDENTIFIANT, null);
            EditText identifiantSaisi = (EditText)findViewById(R.id.identifiantSaisi);
            identifiantSaisi.setText(identifiantConnexionRecupere);
        }
    }

    //fonction déclenchée lors du clic sur le bouton "se connecter" :
    @SuppressLint("SetTextI18n")
    public void connexion(View view) throws ExecutionException, InterruptedException {

        //Récupération des données saisies dans le formulaire de connexion :
        EditText identifiantSaisi = (EditText)findViewById(R.id.identifiantSaisi);
        EditText motDePasseSaisi = (EditText)findViewById(R.id.motDePasse);

        //échappement des balises HTML contre la faille XSS:
        String identifiantRecupere = TextUtils.htmlEncode(identifiantSaisi.getText().toString());
        String motDePasseRecupere = TextUtils.htmlEncode(motDePasseSaisi.getText().toString());

        //Récupération des textview pour les messages d'erreurs :
        TextView msgErreurConnexion = findViewById(R.id.msg_erreur_connexion);
        TextView msgErreurIdentifiant = findViewById(R.id.erreur_id_connexion);
        TextView msgErreurPassword = findViewById(R.id.erreur_password_connexion);

        //si les champs sont renseignés :
        if (!identifiantRecupere.isEmpty() && !motDePasseRecupere.isEmpty()){

                msgErreurConnexion.setText("");
                msgErreurIdentifiant.setText("");
                msgErreurPassword.setText("");
                GererCommercial commercial = new GererCommercial();

                //Vérification de la saisi de l'identifiant :
                 Boolean verificationIdentifiant = commercial.isValidIdentifiant(identifiantRecupere);
                 Boolean verificationPassword = commercial.isValidPassword(motDePasseRecupere);

                 //vérification si l'identigiant et le mot de passe sont biens structuré :
                if(!verificationIdentifiant){
                    msgErreurIdentifiant.setText(R.string.erreurIdentifiant);
                }

                if(!verificationPassword){
                    msgErreurPassword.setText(R.string.erreurMotDePasse);
                }

                //si l'identifiant et le mot de passe sont valides :
                if(verificationIdentifiant && verificationPassword){

                    //connexion internet et appel de la classe de connexion au web service d'authentification :
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    assert cm != null;
                    Log.i("ACOS","Création du ConnectivityManager cm = " + cm.toString());
                    NetworkInfo info = cm.getActiveNetworkInfo();
                    Log.i("ACOS","Récupération d'un objet NetworkInfo info = " + info);

                    //si il y a une connexion internet :
                    if(info != null && info.isConnected())
                    {
                        Log.i("ACOS","Appel de la tâche asynchrone qui va se connecter au web service Ok");

                        //appel du webservice :
                        JSONObject userAuthentifie;
                        WebServiceAuthCnxAsync task = new WebServiceAuthCnxAsync(this);
                        //exécution du webservice et récupération de l'objet du webservice :
                        userAuthentifie = task.execute(identifiantRecupere,motDePasseRecupere).get();

                        String connexionReussi = null;
                        String identifiantEnregistre = null;
                        try {
                            //si le résultat de la requete http n'est pas null alors on récupère la propriété de connexion :
                            if(userAuthentifie != null){
                                connexionReussi = userAuthentifie.getString("isUserLogged");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //si cette propriété de connexion est à true l'utilisateur existe et est correct :
                        if("true".equals(connexionReussi)){

                            try {
                                commercial.setPrenom(userAuthentifie.getString("userSurname"));
                                commercial.setNom(userAuthentifie.getString("userName"));

                                //enregistrer l'identifiant de connexion :
                                identifiantEnregistre = userAuthentifie.getString("userMail");
                                sharedPreferences
                                        .edit()
                                        .putString(PREFS_IDENTIFIANT, identifiantEnregistre)
                                        .apply();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(this, ListeDossiersSAVActivity.class);
                            intent.putExtra("commercial", commercial);
                            startActivity(intent);

                            //sinon on renseigne la zone textview d'erreur mauvais compte :
                        }else{
                            msgErreurIdentifiant.setText("");
                            msgErreurPassword.setText("");
                           String erreurMauvaisCompteMsg = getString(R.string.erreur_msg_mauvais_compte);
                            msgErreurConnexion.setText(erreurMauvaisCompteMsg);
                        }
                        //si il n'y a pas de connexion internet :
                    } else
                    {
                        String erreurCnxInternetMsg = getString(R.string.erreur_connexion_internet);
                        Toast.makeText(this, erreurCnxInternetMsg, Toast.LENGTH_SHORT).show();
                    }
                }

            //Si les champs sont vides :
        } else {
            msgErreurConnexion.setText("");
            msgErreurPassword.setText("");
            //On renvoit un message d'erreur :
            String erreurChampsVidesMsg = getString(R.string.erreurChampsVides);
            msgErreurConnexion.setText(erreurChampsVidesMsg);

        }

    }

}
