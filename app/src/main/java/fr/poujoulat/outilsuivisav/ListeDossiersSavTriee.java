package fr.poujoulat.outilsuivisav;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.poujoulat.outilsuivisav.adapters.GererDossierSavAdapter;
import fr.poujoulat.outilsuivisav.bo.GererDossierSav;
import fr.poujoulat.outilsuivisav.dao.GererDossierSavDao;

public class ListeDossiersSavTriee extends AppCompatActivity {

    private static final String TAG = "ACOS";
    //Objet représentant le recyclerView
    private RecyclerView mRecyclerView;
    //Objet représentant l"adapter remplissant le recyclerView
    private RecyclerView.Adapter mAdapter;
    //Objet permettant de structurer l'ensemble des sous vues contenues dans le RecyclerView.
    private RecyclerView.LayoutManager mLayoutManager;
    //Liste bouchon
    private List<GererDossierSav> dossiers = new ArrayList<>();
    private List<GererDossierSav> listeDossiersSav = new ArrayList<>();
    private String nomclientSaisi ="";
    private String codePostalSaisi ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_dossiers_sav_triee);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        Intent intent = getIntent();
        if (intent != null){

            if (intent.hasExtra("nomClient")){
                nomclientSaisi = intent.getStringExtra("nomClient");
            }
            if (intent.hasExtra("codePostal")){
                codePostalSaisi = intent.getStringExtra("codePostal");
            }

        }

       /*
        if (intent != null){
            if (intent.hasExtra("nomClient")){ // vérifie qu'une valeur est associée à la clé “edittext”
                nomclientSaisi = intent.getStringExtra("nomClient"); // on récupère la valeur associée à la clé
            }
            if (intent.hasExtra("codePostal")){ // vérifie qu'une valeur est associée à la clé “edittext”
                codePostalSaisi = intent.getStringExtra("codePostal"); // on récupère la valeur associée à la clé
            }
        }*/


        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());


        try {
            chargementDossiers(nomclientSaisi, codePostalSaisi);
        } catch (Exception e) {
            e.printStackTrace();
        }
        chargementRecycler();

    }

    /**
     * Définition de l'action du clic sur un item.
     */
    private View.OnClickListener monClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            int position = Integer.parseInt(view.getTag().toString());
            Log.i(TAG,"POSITION : " + view.getTag());
            Intent intent = new Intent(ListeDossiersSavTriee.this, DossierSavActivity.class);
            intent.putExtra("dossier", dossiers.get(position));
            startActivity(intent);
        }
    };

    /**
     * Permet de charger le recycler view
     */
    private void chargementRecycler()
    {

        //Lie le recyclerView aux fonctionnalité offerte par le linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Liaison permettant de structurer l'ensemble des sous vues contenues dans le RecyclerView.
        mAdapter = new GererDossierSavAdapter(dossiers,monClickListener);
        mRecyclerView.setAdapter(mAdapter);
    }



    /**
     * Permet de charger les dossiers sav
     */
    private void chargementDossiers(String nomclientSaisi, String codePostalSaisi) throws Exception {


        try {
            listeDossiersSav = GererDossierSavDao.listeDossiersSavTriee(nomclientSaisi, codePostalSaisi);
            //récupération de la liste des dossiers:
            dossiers = GererDossierSavDao.listeDossiersSavTriee(nomclientSaisi, codePostalSaisi);

        } catch (
                SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



    }


}
