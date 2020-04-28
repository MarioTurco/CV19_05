package com.example.provacv;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import model.Recensione;

public class ListaRecensioniRecycleViewAdapter extends RecyclerView.Adapter<ListaRecensioniRecycleViewAdapter.ViewHolder> {
    private static final String TAG = "ListaRecensioniRecyclerV";
    private MainActivity mainActivity;
    private Context mContext;
    //qui vanno le robe prese dall'activity che a sua volta dovrebbe prenderle dal dao/locale
    private ArrayList<Recensione> listaRecensioni;

    public ListaRecensioniRecycleViewAdapter(Context mContext, ArrayList<Recensione> listaRecensioni,MainActivity activity) {
        this.mContext = mContext;
        this.listaRecensioni = listaRecensioni;
        this.mainActivity = activity;
    }

    @NonNull
    @Override
    public ListaRecensioniRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_recensioni_item, parent, false);
        ListaRecensioniRecycleViewAdapter.ViewHolder holder = new ListaRecensioniRecycleViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaRecensioniRecycleViewAdapter.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");
        final Recensione recensione = listaRecensioni.get(position);

        //TODO scarica immagne dal db
        //qui dobbiamo caricare l'immagine
        //...

        //qui settiamo i vari campi di testo
        holder.autore.setText(recensione.getAutore());
        holder.data.setText(recensione.getDataRecensione());
        holder.ratingBar.setRating(recensione.getValutazione());
        holder.testo.setText(recensione.getTesto());
        holder.titolo.setText(recensione.getTitolo());


        //listener che ci fa aprire la pagina della struttura TODO: implementare listener che apre la pagina della struttura
        holder.ViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, VisualizzaRecensioneFragment.newInstance(recensione), "VisualizzaRecensioneFragment");
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaRecensioni.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView  autore,data,titolo,testo;
        RatingBar ratingBar;
        MaterialCardView ViewLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            attachItemsByID(itemView);
        }

        private void attachItemsByID(View itemView) {
            autore = itemView.findViewById(R.id.autoreRecensione);
            ratingBar = itemView.findViewById(R.id.ratingBarRecensione);
            data = itemView.findViewById(R.id.dataRecensione);
            titolo = itemView.findViewById(R.id.titoloRecensione);
            testo = itemView.findViewById(R.id.testoRecensione);
            ViewLayout = itemView.findViewById(R.id.listItem_layout);
        }
    }
}
