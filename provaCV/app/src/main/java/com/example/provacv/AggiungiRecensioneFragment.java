package com.example.provacv;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
    private ImageButton backButton;

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
        backButton = view.findViewById(R.id.backButton);
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
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                transaction.replace(R.id.container, new DettagliStrutturaFragment(), "dettagliStrutturaFragment");
                transaction.commit();
            }
        });
        aggiungiRecensioneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recensione recensioneDaAggiungere = new Recensione();
                recensioneDaAggiungere.setStruttura(idStruttura);
                recensioneDaAggiungere.setStatoRecensione("In Attesa");
                recensioneDaAggiungere.setAutore(getNickname());
                recensioneDaAggiungere.setValutazione((int)ratingBar.getRating());
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                recensioneDaAggiungere.setDataRecensione(dtf.format(now));
                recensioneDaAggiungere.setTesto(testoRecensione.getText().toString());
                recensioneDaAggiungere.setTitolo( titoloRecensione.getText().toString());
            }
        });
    }

    private String getNickname() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return sharedPreferences.getString("nickname", "");
    }
}
