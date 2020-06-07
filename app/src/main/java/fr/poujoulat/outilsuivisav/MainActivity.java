package fr.poujoulat.outilsuivisav;

import android.content.Context;
import android.content.Intent;
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

import fr.poujoulat.outilsuivisav.dao.WebServiceAuthCnx;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("co", "fonction connexion va etre appellée");

    }

    //fonction déclenchée lors du clic sur le bouton "se connecter" :
    public void connexion(View view) {

        //Récupération des données du formulaire de connexion :
        EditText identifiantSaisi = (EditText)findViewById(R.id.identifiantSaisi);
        EditText motDePasseSaisi = (EditText)findViewById(R.id.motDePasse);
        //échappement des balises HTML :
        String identifiantRecupere = TextUtils.htmlEncode(identifiantSaisi.getText().toString());
        String motDePasseRecupere = TextUtils.htmlEncode(motDePasseSaisi.getText().toString());

        //Récupération des textview des messages d'erreurs :
        TextView msgErreurIdentifiant = findViewById(R.id.msg_erreur_identifiant);
        TextView msgErreurMotDePasse = findViewById(R.id.msg_erreur_motDePasse);
        TextView msgErreurChampsVides = findViewById(R.id.msg_erreur_champs_vides);

        //Récupération id et mot de pass d'un commercial :
        String identifiantCommercial = "id";
        String motDePasseCommercial = "pass";


       //appel de la classe de connexion au web service d'authentification :
            Log.i("ACOS","Entrée dans onClickTransformer");
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            Log.i("ACOS","Création du ConnectivityManager cm = " + cm.toString());
            NetworkInfo info = cm.getActiveNetworkInfo();
            Log.i("ACOS","Récupération d'un objet NetworkInfo info = " + info);
            if(info != null && info.isConnected())
            {
                Log.i("ACOS","Appel de la tâche asynchrone qui va se connecter au web service Ok");
                WebServiceAuthCnx task = new WebServiceAuthCnx();
                task.execute(identifiantRecupere,motDePasseRecupere);
            } else
            { Toast.makeText(MainActivity.this, "Pas internet", Toast.LENGTH_SHORT).show();
            }

        //new ExecuteTask().execute(identifiantRecupere,motDePasseRecupere);

        //si les champs sont renseignés :
        if (identifiantRecupere != "" && motDePasseRecupere != ""){

            msgErreurChampsVides.setText("");
            msgErreurIdentifiant.setText("");
            msgErreurMotDePasse.setText("");

            //si la saisie est correcte, envoit vers la page liste des dossiers SAV :
            if(identifiantRecupere.equals(identifiantCommercial) && motDePasseRecupere.equals(motDePasseCommercial)) {
                Intent intent = new Intent(this, ListeDossiersSAVActivity.class);
                startActivity(intent);

                //si les champs sont incorrects :
            } else {
                if(!identifiantRecupere.equals(identifiantCommercial)){
                    //Récupération d'une valeur string :
                    String msgErreurId = getString(R.string.erreurIdentifiant);
                    //Attribution de cette valeur au textView :
                    msgErreurIdentifiant.setText(msgErreurId);
                }

                if(!motDePasseRecupere.equals(motDePasseCommercial)){
                    String msgErreurMdp = getString(R.string.erreurMotDePasse);
                    msgErreurMotDePasse.setText(msgErreurMdp);
                }
            }
            //Si les champs sont vides :
        } else {
            msgErreurIdentifiant.setText("");
            msgErreurMotDePasse.setText("");
            //On renvoit un message d'erreur :
            String msgErreurChampsVide = getString(R.string.erreurChampsVides);
            msgErreurChampsVides.setText(msgErreurChampsVide);
        }

    }


}
