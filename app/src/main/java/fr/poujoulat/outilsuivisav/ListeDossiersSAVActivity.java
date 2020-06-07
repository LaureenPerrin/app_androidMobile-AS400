package fr.poujoulat.outilsuivisav;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.poujoulat.outilsuivisav.adapters.GererDossierSavAdapter;
import fr.poujoulat.outilsuivisav.bo.GererDossierSav;
import fr.poujoulat.outilsuivisav.dao.GererDossierSavDao;

public class ListeDossiersSAVActivity extends AppCompatActivity {

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
            Intent intent = new Intent(ListeDossiersSAVActivity.this, DossierSavActivity.class);
            intent.putExtra("dossier", dossiers.get(position));
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_dossiers_sav);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

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
            chargementDossiers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        chargementRecycler();
    }

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
    private void chargementDossiers() throws Exception {
        Log.d("dao", "fonction dao appellée");

        listeDossiersSav = GererDossierSavDao.listeDossiersSavTotal();

        //récupération de la liste des dossiers:
        dossiers = GererDossierSavDao.listeDossiersSavTotal();

    }

    public void listeDossierTriee(View view) {
        EditText nomclientTv = (EditText)findViewById(R.id.nomSocieteSaisi);
        EditText codePostalTv = (EditText)findViewById(R.id.codePostalSaisi);

        String  nomclientSaisi = nomclientTv.getText().toString();

        System.out.println(nomclientSaisi.toString());

        String codePostalSaisi = codePostalTv.getText().toString();


            Intent intent = new Intent(this, ListeDossiersSavTriee.class);
            intent.putExtra("nomClient", nomclientSaisi);
            intent.putExtra("codePostal", codePostalSaisi);
            startActivity(intent);

    }
}
