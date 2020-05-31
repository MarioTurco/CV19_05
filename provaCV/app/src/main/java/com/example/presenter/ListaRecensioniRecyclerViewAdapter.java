package com.example.presenter;

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

import java.util.ArrayList;

import model.Recensione;

public class ListaRecensioniRecyclerViewAdapter extends RecyclerView.Adapter<ListaRecensioniRecyclerViewAdapter.ViewHolder> {
    private AppCompatActivity appActivity;

    private ArrayList<Recensione> listaRecensioni;

    public ListaRecensioniRecyclerViewAdapter(ArrayList<Recensione> listaRecensioni, AppCompatActivity activity) {
        this.listaRecensioni = listaRecensioni;
        this.appActivity = activity;
    }

    @NonNull
    @Override
    public ListaRecensioniRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_recensioni_item, parent, false);
        ListaRecensioniRecyclerViewAdapter.ViewHolder holder = new ListaRecensioniRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaRecensioniRecyclerViewAdapter.ViewHolder holder, final int position) {
        Log.d("ListaRecensioniRecyclerView", "onBindViewHolder: ");
        final Recensione recensione = listaRecensioni.get(position);


        //qui settiamo i vari campi di testo
        if(recensione.getAutore().isMostraNickname())
            holder.autore.setText(recensione.getAutore().getNickname());
        else
            holder.autore.setText(recensione.getAutore().getNome());
        holder.data.setText(recensione.getDataRecensione());
        holder.ratingBar.setRating(recensione.getValutazione());
        holder.testo.setText(recensione.getTesto());
        holder.titolo.setText(recensione.getTitolo());


        holder.ViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = appActivity.getSupportFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                transaction.replace(R.id.container, VisualizzaRecensioneFragment.newInstance(recensione), "VisualizzaRecensioneFragment");
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaRecensioni.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView  autore,data,titolo,testo;
        RatingBar ratingBar;
        MaterialCardView ViewLayout;
        ViewHolder(@NonNull View itemView) {
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
