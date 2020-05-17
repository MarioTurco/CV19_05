package com.example.presenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import DAO.RecensioneDAO;
import DAO.VolleyCallback;
import de.hdodenhof.circleimageview.CircleImageView;
import model.Recensione;
import model.Struttura;
import model.Utente;
import utils.DataComparator;
import utils.RatingComparator;


public class DettagliStrutturaFragment extends Fragment {
    private final String TAG = "DettagliStrutturaFragment";
    private ImageButton tastoIndietro;
    private ImageButton filtriRecensioneButton;
    private CircleImageView immagineStruttura;
    private RatingBar ratingBarStruttura;
    private TextView nomeStruttura;
    private TextView descrizioneStruttura;
    private TextView cittaStruttura;
    private TextView indirizzoStruttura;
    private TextView prezzoStruttura;
    private TextView categoriaStruttura;
    private TextView valutazioneRecensione;
    private TextView numeroRecensioni;
    private FloatingActionButton fabButton, fabVisualizzaMappa, fabAggiungiRecensione;
    private ImageButton resetButton;

    //qui vanno le cose da passare all'adapter
    private ArrayList<Recensione> listaRecensioni;
    private static Struttura struttura;
    private RecensioneDAO recensioneDAO;
    private RecyclerView recyclerView;

    private boolean FABAperta = false;

    //costruttore chiamato da un VisualizzaRecensioneFragment
    public DettagliStrutturaFragment() {
        listaRecensioni = new ArrayList<Recensione>();
    }

    public DettagliStrutturaFragment(Struttura struttura) {
        this.struttura = struttura;
        listaRecensioni = new ArrayList<Recensione>();

    }

    private Utente creaUtenteDaQuery(JSONObject result) throws JSONException{
        Utente autoreRecensione = new Utente();
        autoreRecensione.setNickname(result.getString("nickname"));
        autoreRecensione.setNome(result.getString("nome"));
        autoreRecensione.setEmail(result.getString("email"));
        autoreRecensione.setDataDiNascita(result.getString("data_di_nascita"));
        autoreRecensione.setMostraNickname(result.getBoolean("mostra_nickname"));
        autoreRecensione.setPassword(result.getString("password"));
        autoreRecensione.setSalt(result.getString("salt"));
        return autoreRecensione;
    }

    private void caricaRecensioniStruttura() {
        recensioneDAO.getRecensioniPerIdStruttura(struttura.getIdStruttura(),
                new VolleyCallback<JSONArray>() {
                    @Override
                    public void onSuccess(JSONArray result) {
                        try {
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject recensioneJSON = result.getJSONObject(i);
                                Recensione recensione = new Recensione();
                                recensione.setTesto(recensioneJSON.getString("testo"));
                                recensione.setDataRecensione(recensioneJSON.getString("datarecensione"));
                                recensione.setTitolo(recensioneJSON.getString("titolo"));
                                Utente autore = creaUtenteDaQuery(recensioneJSON);
                                recensione.setAutore(autore);
                                recensione.setStruttura(struttura);
                                recensione.setIdRecensione(recensioneJSON.getInt("id_recensione"));
                                recensione.setValutazione(recensioneJSON.getInt("valutazione"));
                                listaRecensioni.add(recensione);
                            }
                            initRecyclerViewSenzaFiltri();
                            numeroRecensioni.setText("(" + listaRecensioni.size() + ")");
                            Log.d(TAG, "onSuccess: " + listaRecensioni.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail() {

                    }

                    private void initRecyclerViewSenzaFiltri() {
                        ListaRecensioniRecycleViewAdapter recyclerViewAdapter = new ListaRecensioniRecycleViewAdapter(listaRecensioni, (MainActivity) getActivity());
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                });
    }

    public static Fragment newInstance(Struttura struttura) {
        return new DettagliStrutturaFragment(struttura);
    }

    private void referenziaElementiUI(View view) {
        recyclerView = view.findViewById(R.id.recensioniRecyclerView);
        nomeStruttura = view.findViewById(R.id.nomeStruttura);
        ratingBarStruttura = view.findViewById(R.id.ratingBarStruttura);
        descrizioneStruttura = view.findViewById(R.id.descrizioneStruttura);
        cittaStruttura = view.findViewById(R.id.cittàStruttura);
        indirizzoStruttura = view.findViewById(R.id.indirizzoStruttura);
        tastoIndietro = view.findViewById(R.id.backButtonSignup);
        prezzoStruttura = view.findViewById(R.id.prezzoStruttura);
        categoriaStruttura = view.findViewById(R.id.categoriaStruttura);
        valutazioneRecensione = view.findViewById(R.id.valutazioneRecensione);
        numeroRecensioni = view.findViewById(R.id.numeroRecensioni);
        fabButton = view.findViewById(R.id.floatingActionButtonStruttra);
        fabAggiungiRecensione = view.findViewById(R.id.fabAggiungiRecensione);
        fabVisualizzaMappa = view.findViewById(R.id.fabVisualizzaSuMappa);
        tastoIndietro = view.findViewById(R.id.backButtonStruttura);
        immagineStruttura = view.findViewById(R.id.immagine);
        filtriRecensioneButton = view.findViewById(R.id.filtriRecensioneButton);
        resetButton = view.findViewById(R.id.resetButton);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recensioneDAO = new RecensioneDAO(this.getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_struttura, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        referenziaElementiUI(view);
        caricaRecensioniStruttura();
        mostraDettagliStruttura();
        aggiungiListenerTastoIndietro();
        configuraFloatingActionButton();
        final Fragment thisFragment = this;
        filtriRecensioneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                DialogFragment dialogFragment = new FiltriRecensioniDialog();
                dialogFragment.setTargetFragment(thisFragment, 100);
                dialogFragment.show(ft, "dialog");
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRecyclerViewConFiltri(listaRecensioni);
            }
        });
    }

    private void configuraFloatingActionButton() {
        final Animation fabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        final Animation fabClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        final Animation rotateBackward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);
        final Animation rotateForward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        final MainActivity activity = (MainActivity) this.getActivity();
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean UserLogged = utenteLoggato();
                if (FABAperta) {
                    fabButton.startAnimation(rotateBackward);
                    fabVisualizzaMappa.startAnimation(fabClose);
                    if (UserLogged)
                        fabAggiungiRecensione.startAnimation(fabClose);
                    fabAggiungiRecensione.setClickable(false);
                    fabVisualizzaMappa.setClickable(false);
                    FABAperta = false;
                } else {
                    fabButton.startAnimation(rotateForward);
                    fabVisualizzaMappa.startAnimation(fabOpen);
                    if (UserLogged) {
                        fabAggiungiRecensione.startAnimation(fabOpen);
                        fabAggiungiRecensione.setClickable(true);
                    }
                    fabVisualizzaMappa.setClickable(true);
                    FABAperta = true;
                }
            }
        });

        fabAggiungiRecensione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                transaction.replace(R.id.container, AggiungiRecensioneFragment.newInstance(struttura.getIdStruttura()), "ConnessioneAssenteFragment");
                transaction.commit();
            }
        });

        fabVisualizzaMappa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                transaction.replace(R.id.container, VisualizzaStrutturaSuMappa.newInstance(struttura), "Dettagli struttura to visualizza su mappa");
                transaction.commit();
                */
                 //Mostra dialog
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                DialogFragment dialogFragment = new VisualizzaSuMappaDialog(struttura);
                dialogFragment.show(ft, "dialog");
            }
        });

    }

    private boolean utenteLoggato() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return sharedPreferences.getBoolean("isLogged", false);
    }


    private void aggiungiListenerTastoIndietro() {
        tastoIndietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ListaStruttureFragment.listaStrutture == null){
                    ((MainActivity)getActivity()).setMap();
                }
                else {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                    transaction.replace(R.id.container, ListaStruttureFragment.newInstance(), "ListStruttureFragment");
                    transaction.commit();
                }
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if(ListaStruttureFragment.listaStrutture == null){
                    ((MainActivity)getActivity()).setMap();
                }
                else {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                    transaction.replace(R.id.container, ListaStruttureFragment.newInstance(), "ListStruttureFragment");
                    transaction.commit();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void mostraDettagliStruttura() {
        nomeStruttura.setText(struttura.getNome());
        ratingBarStruttura.setRating((float) struttura.getValutazioneMedia());
        descrizioneStruttura.setText(struttura.getDescrizione());
        cittaStruttura.setText(struttura.getCittà());
        indirizzoStruttura.setText(struttura.getIndirizzo());
        prezzoStruttura.setText(struttura.getFasciaDiPrezzo());
        categoriaStruttura.setText(struttura.getCategoria());
        valutazioneRecensione.setText(String.valueOf(struttura.getValutazioneMedia()));

        Picasso.get().load(Uri.parse(struttura.getUrlFoto()))
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(immagineStruttura);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {

            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Recensione> listaFiltrata = filtraListaRecensioni(data.getExtras());
                initRecyclerViewConFiltri(listaFiltrata);
            }
        }
    }

    private boolean controllaNomeONicknameFiltro(Recensione recensione, String autoreFiltro){
        boolean controlloNomeONickname = true;
        if(!autoreFiltro.equals("")) {
            if (recensione.getAutore().isMostraNickname())
                controlloNomeONickname = recensione.getAutore().getNickname().contains(autoreFiltro);
            else
                controlloNomeONickname = recensione.getAutore().getNome().contains(autoreFiltro);
        }
        return controlloNomeONickname;
    }

    private boolean controllaRatingFiltro(Recensione recensione, int ratingFiltro){
        return (recensione.getValutazione() == ratingFiltro) || (ratingFiltro == 0);
    }

    private void ordinaListaFiltrata(ArrayList<Recensione> listaFiltrata, boolean ordinaPerData){
        if(ordinaPerData)
            Collections.sort(listaFiltrata, new DataComparator());
        else
            Collections.sort(listaFiltrata, new RatingComparator());
        Collections.reverse(listaFiltrata);
    }

    private ArrayList<Recensione> filtraListaRecensioni(Bundle filtri){
        ArrayList<Recensione> listaFiltrata = new ArrayList<>();
        for(Recensione recensione : listaRecensioni){
            if(controllaNomeONicknameFiltro(recensione, filtri.getString("Autore"))
                    && controllaRatingFiltro(recensione, filtri.getInt("Rating")))
                listaFiltrata.add(recensione);
        }
        System.out.println(listaFiltrata);
        ordinaListaFiltrata(listaFiltrata, filtri.getBoolean("Recenti"));
        return listaFiltrata;
    }

    private void initRecyclerViewConFiltri(ArrayList<Recensione> recensioniFiltrate) {
        ListaRecensioniRecycleViewAdapter recyclerViewAdapter = new ListaRecensioniRecycleViewAdapter(recensioniFiltrate, (MainActivity) getActivity());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
