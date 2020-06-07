package fr.poujoulat.outilsuivisav.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.poujoulat.outilsuivisav.R;
import fr.poujoulat.outilsuivisav.bo.GererDossierSav;

public class GererDossierSavAdapter extends RecyclerView.Adapter<GererDossierSavAdapter.ViewHolder>{


   List<GererDossierSav> dossiers = null;
    private View.OnClickListener monClickListener;

    /**
     *
     * @param dossiers
     * @param monClickListener
     */
    public GererDossierSavAdapter(List<GererDossierSav> dossiers, View.OnClickListener monClickListener) {
        this.dossiers = dossiers;
        this.monClickListener = monClickListener;
    }


    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_cards, parent, false);

        ViewHolder vh = new ViewHolder(v,monClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull GererDossierSavAdapter.ViewHolder holder, int position) {
        //Attribution des valeurs d'un dossier dans les text view de la card view :
        int id = dossiers.get(position).getId();
        holder.titreDossier.setText(R.string.id_dossier);
        holder.id.setText(String.valueOf(id));
        holder.titreDateCrea.setText(R.string.date_creation);
        holder.dateCreation.setText(dossiers.get(position).getDateCreation());
        holder.titreNomClient.setText(R.string.nom_client);
        holder.nomClient.setText(dossiers.get(position).getNomClient());
//        holder.titreStatut.setText(R.string.statut);
    //    holder.statut.setText(dossiers.get(position).getStatut());
        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return dossiers.size();
    }


    /**
     * Classe interne
     */
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView titreDossier;
        public TextView id;
        public TextView titreDateCrea;
        public TextView dateCreation;
        public TextView titreNomClient;
        public TextView nomClient;
        public TextView titreStatut;
        public TextView statut;


        public ViewHolder(View v, View.OnClickListener monClickListener)
        {
            super(v);
            titreDossier = v.findViewById(R.id.tv_cv_titreIdDossier);
            id = v.findViewById(R.id.tv_cv_idDossier);
            titreDateCrea = v.findViewById(R.id.tv_cv_titreDatecrea);
            dateCreation = v.findViewById(R.id.tv_cv_dateCrea);
            titreNomClient = v.findViewById(R.id.tv_cv_titreNomClient);
            nomClient = v.findViewById(R.id.tv_cv_nomClient);
            titreStatut = v.findViewById(R.id.tv_cv_titreStatut);
            statut = v.findViewById(R.id.tv_cv_statut);

            v.setOnClickListener(monClickListener);
        }
    }




}
