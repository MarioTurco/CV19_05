package com.example.provacv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import DAO.RecensioneDAO;
import model.Recensione;

public class AggiungiRecensioneFragment extends Fragment {
    private int idStruttura;
    private TextView titoloRecensione;
    private TextView testoRecensione;
    private RatingBar ratingBar;
    private Button aggiungiRecensioneButton;
    private RecensioneDAO recensioneDAO;

    public AggiungiRecensioneFragment(int idStruttura){
        this.idStruttura = idStruttura;
        this.recensioneDAO = new RecensioneDAO(this.getActivity());
    }

    public static AggiungiRecensioneFragment newInstance(int idStruttura) {
        AggiungiRecensioneFragment fragment = new AggiungiRecensioneFragment(idStruttura);
        return fragment;
    }

    private void inizializeUiElements(View view){
        titoloRecensione = view.findViewById(R.id.titoloRecensione);
        testoRecensione = view.findViewById(R.id.testoRecensione);
        ratingBar = view.findViewById(R.id.ratingBar);
        aggiungiRecensioneButton = view.findViewById(R.id.aggiungiRecensioneButton);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aggiungi_recensione, container, false);
        inizializeUiElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        aggiungiRecensioneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recensione recensioneDaAggiungere = new Recensione();
                recensioneDaAggiungere.setStruttura(idStruttura);
                recensioneDaAggiungere.setStatoRecensione("In Attesa");
                recensioneDaAggiungere.setAutore("TODO"); //TODO prendere l'autore dalle shared preferences
                recensioneDaAggiungere.setValutazione((int)ratingBar.getRating());
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                recensioneDaAggiungere.setDataRecensione(dtf.format(now));
                recensioneDaAggiungere.setTesto(testoRecensione.getText().toString());
                recensioneDaAggiungere.setTitolo( titoloRecensione.getText().toString());
            }
        });
    }
}
