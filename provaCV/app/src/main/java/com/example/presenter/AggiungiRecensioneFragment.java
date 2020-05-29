package com.example.presenter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import DAO.RecensioneDAO;
import DAO.VolleyCallback;
import model.Recensione;
import model.Struttura;
import model.Utente;

public class AggiungiRecensioneFragment extends Fragment {
    final String TAG = "AggiungiRecensioneFragment";

    private int idStruttura;
    private TextView titoloRecensione;
    private TextView testoRecensione;
    private RatingBar ratingBar;
    private Button aggiungiRecensioneButton;
    private RecensioneDAO recensioneDAO;
    private ImageButton backButton;

    public AggiungiRecensioneFragment(int idStruttura){
        this.idStruttura = idStruttura;
    }

    public static AggiungiRecensioneFragment newInstance(int idStruttura) {
        return new AggiungiRecensioneFragment(idStruttura);
    }

    private void referenziaElementiUI(View view){
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
        referenziaElementiUI(view);
        this.recensioneDAO = new RecensioneDAO(this.getActivity());
        return view;
    }

    private boolean controllaCampiNonVuoti(){
        return !titoloRecensione.getText().toString().equals("") &&
                !testoRecensione.getText().toString().equals("") &&
                ratingBar.getRating() != 0;
    }

    private Recensione costruisciRecensioneDaInserire(){
        Recensione recensioneDaAggiungere = new Recensione();
        Struttura struttura = new Struttura();
        struttura.setIdStruttura(idStruttura);
        if(controllaCampiNonVuoti()) {
            recensioneDaAggiungere.setStruttura(struttura);
            recensioneDaAggiungere.setStatoRecensione("In Attesa");
            Utente autore = new Utente();
            autore.setNickname(getNickname());
            recensioneDaAggiungere.setAutore(autore);
            recensioneDaAggiungere.setValutazione((int) ratingBar.getRating());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu");
            LocalDateTime now = LocalDateTime.now();
            recensioneDaAggiungere.setDataRecensione(dtf.format(now));
            recensioneDaAggiungere.setTesto(testoRecensione.getText().toString());
            recensioneDaAggiungere.setTitolo(titoloRecensione.getText().toString());
        }
        else throw new IllegalArgumentException("Compila tutti i campi!");
        return recensioneDaAggiungere;
    }

    private void tornaIndietroStruttura(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
        transaction.replace(R.id.container, new DettagliStrutturaFragment(), "dettagliStrutturaFragment");
        transaction.commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tornaIndietroStruttura();
            }
        });
        aggiungiRecensioneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Recensione recensioneDaAggiungere = costruisciRecensioneDaInserire();
                    //chiamata metodo con Recensione in ingresso con le istruzioni seguenti
                    recensioneDAO.aggiungiRecensione(recensioneDaAggiungere, new VolleyCallback<String>() {

                        private String getMessaggioErrore(String result){
                            if(result.contains("check_uniq_recensione"))
                                return "Una recensione è già stata aggiunta a questa struttura!";
                            else return "La richiesta non è andata a buon fine";
                        }
                        @Override
                        public void onSuccess(String result) {
                            if (result.contains("successfully")) {
                                Log.d(TAG, "onSuccess: Recensione Aggiunta");
                                Toast.makeText(getContext(), "Recensione Aggiunta", Toast.LENGTH_SHORT).show();
                                backButton.performClick();
                            } else {
                                System.out.println(result);
                                Log.d(TAG, "onSuccess: recensione fallito");
                                Toast.makeText(getContext(), getMessaggioErrore(result), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFail() {
                            Toast.makeText(getContext(), "Il server non è al momento raggiungibile", Toast.LENGTH_LONG).show();
                     /*   FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                        transaction.add(R.id.container, ConnessioneAssenteFragment.newInstance(), "Connessione Assente");
                        transaction.commit();*/
                        }
                    });
                } catch (IllegalArgumentException ex) {
                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private String getNickname() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return sharedPreferences.getString("nickname", "");
    }
}
