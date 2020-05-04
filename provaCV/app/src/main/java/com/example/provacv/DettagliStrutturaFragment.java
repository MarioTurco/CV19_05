package com.example.provacv;

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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import DAO.RecensioneDAO;
import DAO.VolleyCallback;
import de.hdodenhof.circleimageview.CircleImageView;
import model.Recensione;
import model.Struttura;


public class DettagliStrutturaFragment extends Fragment {
    private final String TAG = "DettagliStrutturaFragment";
    private ImageButton backButton;

    private CircleImageView immagineStruttura;

    private RatingBar ratingBarStruttura;

    private TextView nomeStruttura;
    private TextView descrizioneStruttura;
    private TextView cittàStruttura;
    private TextView indirizzoStruttura;
    private TextView prezzoStruttura;
    private TextView categoriaStruttura;
    private TextView valutazioneRecensione;
    private TextView numeroRecensioni;
    private FloatingActionButton fabButton, fabVisualizzaMappa, fabAggiungiRecensione;

    //qui vanno le cose da passare all'adapter
    private ArrayList<Recensione> listaRecensioni;
    private static Struttura struttura;
    private RecensioneDAO recensioneDAO;
    private RecyclerView recyclerView;

    private boolean isFabOpen = false;

    //costruttore chiamato da un VisualizzaRecensioneFragment
    public DettagliStrutturaFragment() {
        listaRecensioni = new ArrayList<Recensione>();
    }

    public DettagliStrutturaFragment(Struttura struttura) {
        this.struttura = struttura;
        listaRecensioni = new ArrayList<Recensione>();

    }

    private void caricaRecensioniStruttura() {
        recensioneDAO.getRecensioniByIdStruttura(struttura.getIdStruttura(),
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
                                if(recensioneJSON.getString("mostra_nickname").equals("True"))
                                    recensione.setAutore(recensioneJSON.getString("autore"));
                                else
                                    recensione.setAutore(recensioneJSON.getString("nome"));
                                recensione.setStruttura(struttura.getIdStruttura());
                                recensione.setIdRecensione(recensioneJSON.getInt("id_recensione"));
                                recensione.setValutazione(recensioneJSON.getInt("valutazione"));
                                listaRecensioni.add(recensione);
                            }
                            initRecyclerView();
                            numeroRecensioni.setText("(" + listaRecensioni.size() + ")");
                            Log.d(TAG, "onSuccess: " + listaRecensioni.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail() {

                    }

                    private void initRecyclerView() {
                        ListaRecensioniRecycleViewAdapter recyclerViewAdapter = new ListaRecensioniRecycleViewAdapter(getContext(), listaRecensioni, (MainActivity) getActivity());
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                });
    }

    public static Fragment newInstance(Struttura struttura) {
        return new DettagliStrutturaFragment(struttura);
    }

    private void initGUIElements(View view) {
        recyclerView = view.findViewById(R.id.recensioniRecyclerView);
        nomeStruttura = view.findViewById(R.id.nomeStruttura);
        ratingBarStruttura = view.findViewById(R.id.ratingBarStruttura);
        descrizioneStruttura = view.findViewById(R.id.descrizioneStruttura);
        cittàStruttura = view.findViewById(R.id.cittàStruttura);
        indirizzoStruttura = view.findViewById(R.id.indirizzoStruttura);
        backButton = view.findViewById(R.id.backButtonSignup);
        prezzoStruttura = view.findViewById(R.id.prezzoStruttura);
        categoriaStruttura = view.findViewById(R.id.categoriaStruttura);
        valutazioneRecensione = view.findViewById(R.id.valutazioneRecensione);
        numeroRecensioni = view.findViewById(R.id.numeroRecensioni);
        fabButton = view.findViewById(R.id.floatingActionButtonStruttra);
        fabAggiungiRecensione = view.findViewById(R.id.fabAggiungiRecensione);
        fabVisualizzaMappa = view.findViewById(R.id.fabVisualizzaSuMappa);
        backButton = view.findViewById(R.id.backButtonStruttura);
        immagineStruttura = view.findViewById(R.id.immagine);

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
        setUpBackButton();
        setupFAB();
    }

    private void setupFAB() {
        final Animation fabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        final Animation fabClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        final Animation rotateBackward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);
        final Animation rotateForward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        final MainActivity activity = (MainActivity) this.getActivity();
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean UserLogged = isUserLogged();
                if (isFabOpen) {
                    fabButton.startAnimation(rotateBackward);
                    fabVisualizzaMappa.startAnimation(fabClose);
                    if (UserLogged)
                        fabAggiungiRecensione.startAnimation(fabClose);
                    fabAggiungiRecensione.setClickable(false);
                    fabVisualizzaMappa.setClickable(false);
                    isFabOpen = false;
                } else {
                    fabButton.startAnimation(rotateForward);
                    fabVisualizzaMappa.startAnimation(fabOpen);
                    if (UserLogged) {
                        fabAggiungiRecensione.startAnimation(fabOpen);
                        fabAggiungiRecensione.setClickable(true);
                    }
                    fabVisualizzaMappa.setClickable(true);
                    isFabOpen = true;
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
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                transaction.replace(R.id.container, VisualizzaStrutturaSuMappa.newInstance(struttura), "Dettagli struttura to visualizza su mappa");
                transaction.commit();
            }
        });

    }

    private boolean isUserLogged() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return sharedPreferences.getBoolean("isLogged", false);
    }


    private void setUpBackButton() {
        backButton.setOnClickListener(new View.OnClickListener() {
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
        cittàStruttura.setText(struttura.getCittà());
        indirizzoStruttura.setText(struttura.getIndirizzo());
        prezzoStruttura.setText(struttura.getFasciaDiPrezzo());
        categoriaStruttura.setText(struttura.getCategoria());
        valutazioneRecensione.setText(String.valueOf(struttura.getValutazioneMedia()));

        Picasso.get().load(Uri.parse(struttura.getUrlFoto()))
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(immagineStruttura);
    }
}
