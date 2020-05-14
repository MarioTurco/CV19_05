package com.example.presenter;

import android.content.Context;
import android.net.Uri;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import model.Struttura;

public class ListaStruttureRecyclerViewAdapter extends RecyclerView.Adapter<ListaStruttureRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ListaStruttureRecyclerV";

    private MainActivity mainActivity;
    private Context mContext;
    //qui vanno le robe prese dall'activity che a sua volta dovrebbe prenderle dal dao/locale
    private ArrayList<Struttura> listaStruttura;

    public ListaStruttureRecyclerViewAdapter(Context mContext, ArrayList<Struttura> listaStrutture, MainActivity activity ) {
        this.mContext = mContext;
        this.listaStruttura = listaStrutture;
        this.mainActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_strutture_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");
        final Struttura struttura = listaStruttura.get(position);

        //TODO scarica immagne dal db
        //qui dobbiamo caricare l'immagine
        //...

        //qui settiamo i vari campi di testo
        holder.nome.setText(struttura.getNome());
        holder.categoria.setText(struttura.getCategoria());
        holder.città.setText(struttura.getCittà());
        holder.descrizione.setText(struttura.getDescrizione());
        holder.ratingBar.setRating((float)struttura.getValutazioneMedia());

        //foto

        Picasso.get().load(Uri.parse(struttura.getUrlFoto()))
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.immagine);

        holder.ViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out, R.anim.zoom_in, R.anim.zoom_out);
                       // .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                transaction.replace(R.id.container, DettagliStrutturaFragment.newInstance(struttura), "DettagliStrutturaFragment");
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaStruttura.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView immagine;
        TextView nome, città, descrizione, categoria;
        RatingBar ratingBar;
        MaterialCardView ViewLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            attachItemsByID(itemView);
        }

        private void attachItemsByID(View itemView) {
            immagine = itemView.findViewById(R.id.listItem_immagineStruttura);
            nome = itemView.findViewById(R.id.recensione_TitoloRecensione);
            città = itemView.findViewById(R.id.listItem_cittàStruttura);
            descrizione = itemView.findViewById(R.id.listItem_descrizioneStruttura);
            categoria = itemView.findViewById(R.id.listItem_categoriaStruttura);
            ratingBar = itemView.findViewById(R.id.listItem_ratingStruttura);
            ViewLayout = itemView.findViewById(R.id.listItem_layout);
        }
    }
}