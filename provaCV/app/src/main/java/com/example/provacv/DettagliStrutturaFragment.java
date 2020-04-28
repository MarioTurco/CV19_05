package com.example.provacv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import DAO.RecensioneDAO;
import DAO.VolleyCallback;
import model.Recensione;
import model.Struttura;


public class DettagliStrutturaFragment extends Fragment {
    private ImageButton backButton;
    private TextView nomeStruttura;
    private ImageView immagineStruttura;
    private RatingBar ratingBarStruttura;
    private TextView descrizioneStruttura;
    private TextView cittàStruttura;
    private TextView indirizzoStruttura;
    private TextView prezzoStruttura;
    private TextView categoriaStruttura;
    //qui vanno le cose da passare all'adapter
    private static ArrayList<Recensione> listaRecensioni;
    private static Struttura struttura;
    private RecensioneDAO recensioneDAO;
    private RecyclerView recyclerView;

    public DettagliStrutturaFragment(){

    }

    public DettagliStrutturaFragment(Struttura struttura){
        this.struttura = struttura;
        listaRecensioni = new ArrayList<Recensione>();

    }

    private void caricaRecensioniStruttura(){
        recensioneDAO.getRecensioniByIdStruttura(struttura.getIdStruttura(),
                new VolleyCallback<JSONArray>(){
                    @Override
                    public void onSuccess(JSONArray result) {
                        System.out.println(result.length());
                        try {
                            for (int i = 0; i < result.length(); i++) {

                                JSONObject recensioneJSON = result.getJSONObject(i);
                                Recensione recensione = new Recensione();
                                recensione.setTesto(recensioneJSON.getString("testo"));
                                recensione.setDataRecensione(recensioneJSON.getString("datarecensione"));
                                recensione.setTitolo(recensioneJSON.getString("titolo"));
                                recensione.setAutore(recensioneJSON.getString("autore"));
                                recensione.setStruttura(struttura.getIdStruttura());
                                recensione.setIdRecensione(recensioneJSON.getInt("id_recensione"));
                                recensione.setValutazione(recensioneJSON.getInt("valutazione"));

                                listaRecensioni.add(recensione);


                            }
                            initRecyclerView();
                            System.out.println(listaRecensioni);
                        }
                        catch(JSONException e){
                            System.out.print("NONHAFUNZIONATO");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(){

                    }

                    private void initRecyclerView(){
                        ListaRecensioniRecycleViewAdapter recyclerViewAdapter = new ListaRecensioniRecycleViewAdapter(getContext(), listaRecensioni, (MainActivity) getActivity());
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                });
    }

    public static Fragment newInstance(Struttura struttura) {
        return new DettagliStrutturaFragment(struttura);
    }

    private void initGUIElements(View view){
        recyclerView = view.findViewById(R.id.recensioniRecyclerView);
        nomeStruttura =  view.findViewById(R.id.nomeStruttura);
        ratingBarStruttura =  view.findViewById(R.id.ratingBarStruttura);
        descrizioneStruttura =  view.findViewById(R.id.descrizioneStruttura);
        cittàStruttura =  view.findViewById(R.id.cittàStruttura);
        indirizzoStruttura = view.findViewById(R.id.indirizzoStruttura);
        backButton = view.findViewById(R.id.backButtonSignup);
        prezzoStruttura = view.findViewById(R.id.prezzoStruttura);
        categoriaStruttura = view.findViewById(R.id.categoriaStruttura);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recensioneDAO = new RecensioneDAO(this.getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_struttura, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initGUIElements(view);
        caricaRecensioniStruttura();
        mostraDettagliStruttura();
        backButton = view.findViewById(R.id.backButtonStruttura);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, ListaStruttureFragment.newInstance(), "ListStruttureFragment");
                transaction.commit();
            }
        });
    }

    private void mostraDettagliStruttura(){
        nomeStruttura.setText(struttura.getNome());
        ratingBarStruttura.setRating((float)struttura.getValutazioneMedia());
        descrizioneStruttura.setText(struttura.getDescrizione());
        cittàStruttura.setText(struttura.getCittà());
        indirizzoStruttura.setText(struttura.getIndirizzo());
        prezzoStruttura.setText(struttura.getFasciaDiPrezzo());
        categoriaStruttura.setText(struttura.getCategoria());
    }


}
