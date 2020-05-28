package com.example.presenter;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import model.Struttura;

public class ListaStruttureRecyclerViewAdapter extends RecyclerView.Adapter<ListaStruttureRecyclerViewAdapter.ViewHolder> {
    private AppCompatActivity appActivity;
    private ArrayList<Struttura> listaStruttura;

    public ListaStruttureRecyclerViewAdapter(ArrayList<Struttura> listaStrutture, AppCompatActivity activity) {
        this.listaStruttura = listaStrutture;
        this.appActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_strutture_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d("ListaStruttureRecyclerV", "onBindViewHolder: ");
        final Struttura struttura = listaStruttura.get(position);
        holder.nome.setText(struttura.getNome());
        holder.categoria.setText(struttura.getCategoria());
        holder.citta.setText(struttura.getCittà());
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
                FragmentTransaction transaction = appActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out, R.anim.zoom_in, R.anim.zoom_out);
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

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView immagine;
        TextView nome, citta, descrizione, categoria;
        RatingBar ratingBar;
        MaterialCardView ViewLayout;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            attachItemsByID(itemView);
        }

        private void attachItemsByID(View itemView) {
            immagine = itemView.findViewById(R.id.listItem_immagineStruttura);
            nome = itemView.findViewById(R.id.recensione_TitoloRecensione);
            citta = itemView.findViewById(R.id.listItem_cittàStruttura);
            descrizione = itemView.findViewById(R.id.listItem_descrizioneStruttura);
            categoria = itemView.findViewById(R.id.listItem_categoriaStruttura);
            ratingBar = itemView.findViewById(R.id.listItem_ratingStruttura);
            ViewLayout = itemView.findViewById(R.id.listItem_layout);
        }
    }
}
