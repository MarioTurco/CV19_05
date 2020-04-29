package com.example.provacv;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import DAO.StrutturaDAO;
import DAO.VolleyCallback;
import model.Struttura;

import static com.android.volley.VolleyLog.TAG;


public class FiltriFragment extends Fragment {
    private static final String TAG = "FiltriFragment";
    private static final int MY_PERMISSIOS_REQUEST_LOCATION = 1;
    private ImageButton backButton;
    private ViewGroup container;
    private SupportMapFragment mapFragment;
    private Button cercaButton;
    private EditText nomeText;
    private EditText cittaText;
    private EditText distanzaText;
    private Spinner spinnerCategoria;
    private Spinner spinnerPrezzo;
    private Spinner spinnerValutazione;
    private Switch prossimitàSwitch;
    private MainActivity activity;
    private StrutturaDAO strutturaDAO;

    public FiltriFragment() {

    }


    public static FiltriFragment newInstance() {
        FiltriFragment fragment = new FiltriFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.strutturaDAO = new StrutturaDAO(this.getActivity());
    }

    private void initSpinnerCategoria(View view, ArrayAdapter<CharSequence> adapter){
        adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.categoria_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria = (Spinner) view.findViewById(R.id.spinnerCategoria);
        spinnerCategoria.setAdapter(adapter);
    }

    private void initSpinnerPrezzo(View view, ArrayAdapter<CharSequence> adapter){
        adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.prezzo_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrezzo = (Spinner) view.findViewById(R.id.spinnerPrezzo);
        spinnerPrezzo.setAdapter(adapter);
    }

    private void initSpinnerValutazione(View view, ArrayAdapter<CharSequence> adapter){
        adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.valutazione_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerValutazione = (Spinner) view.findViewById(R.id.spinnerValutazione);
        spinnerValutazione.setAdapter(adapter);
    }

    private void initSpinners(View view, ArrayAdapter<CharSequence> adapter){
        initSpinnerCategoria(view, adapter);
        initSpinnerPrezzo(view, adapter);
        initSpinnerValutazione(view, adapter);
    }



    private void initGUIElements(View view){
        ArrayAdapter<CharSequence> adapter = null;

        cercaButton = (Button) view.findViewById(R.id.cercaButton);
        nomeText = (EditText) view.findViewById(R.id.nomeText);
        cittaText = (EditText) view.findViewById(R.id.cittaText);
        distanzaText = (EditText) view.findViewById(R.id.distanzaText);
        prossimitàSwitch = view.findViewById(R.id.prossimitàSwitch);
        backButton = view.findViewById(R.id.backButtonSignup);

        initSpinners(view, adapter);

        setupProssimitàSwitch();
    }

    private boolean hasFineLocationAccess(){
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }
    private boolean hasCoarseLocationAccess(){
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }
    private boolean hasGPSPermissions(){
        return hasFineLocationAccess() && hasCoarseLocationAccess();
    }
    private void setupProssimitàSwitch() {
        prossimitàSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if (hasGPSPermissions()) {
                        abilitaProssimità();
                    }
                    else{
                        askForGPSPermissions();
                    }
                }
                else{
                    disabilitaProssimità();
                }
            }
        });
    }

    private void askForGPSPermissions() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIOS_REQUEST_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIOS_REQUEST_LOCATION:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    Log.d(TAG, "onRequestPermissionsResult: Abilitato");
                    abilitaProssimità();
                }
                else{
                    Log.d(TAG, "onRequestPermissionsResult: disbilitato");
                    disabilitaProssimità();
                }
            }
        }
    }

    private void disabilitaProssimità() {
        distanzaText.setVisibility(View.INVISIBLE);
        cittaText.setVisibility(View.VISIBLE);
        prossimitàSwitch.setChecked(false);
    }


    private void abilitaProssimità() {
        distanzaText.setVisibility(View.VISIBLE);
        cittaText.setVisibility(View.INVISIBLE);
        prossimitàSwitch.setChecked(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtri, container, false);
        initGUIElements(view);
        activity = (MainActivity)getActivity();
        cercaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).toolbar.setVisibility(View.GONE);
                cercaStrutture();

            }
        });
        this.container = container;
        return view;
    }


    private void cercaStrutture(){
        String distanzaMassima;
        String citta;


        if(prossimitàSwitch.isChecked()) {
            citta = "";
            distanzaMassima = distanzaText.getText().toString();
        }
        else{
            distanzaMassima = "";
            citta = cittaText.getText().toString();
        }

        Filtri filtriStruttura = new Filtri(nomeText.getText().toString(),
                                            citta,
                                            spinnerCategoria.getSelectedItem().toString(),
                                            spinnerPrezzo.getSelectedItem().toString(),
                                            spinnerValutazione.getSelectedItem().toString(),
                                            distanzaMassima,
                                            prossimitàSwitch.isChecked()
                                            );

        strutturaDAO.strutturaQuery(filtriStruttura,
                new VolleyCallback<JSONArray>() {

                    @Override
                    public void onFail() {
                        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                        transaction.replace(R.id.container, ConnessioneAssenteFragment.newInstance(), "ConnessioneAssenteFragment");
                        transaction.commit();
                    }

                    @Override
                    public void onSuccess(JSONArray result) {
                        ArrayList<Struttura> listaStrutture = new ArrayList<>();
                        try {
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject strutturaJSON = result.getJSONObject(i);
                                Struttura strutturaObject = new Struttura();
                                strutturaObject.setNome(strutturaJSON.getString("nome"));
                                strutturaObject.setIndirizzo(strutturaJSON.getString("indirizzo"));
                                strutturaObject.setLatitudine(strutturaJSON.getDouble("latitudine"));
                                strutturaObject.setLongitudine(strutturaJSON.getDouble("longitudine"));
                                strutturaObject.setDescrizione(strutturaJSON.getString("descrizione"));
                                strutturaObject.setCittà(strutturaJSON.getString("citta"));
                                strutturaObject.setIdStruttura(strutturaJSON.getInt("id_struttura"));
                                strutturaObject.setValutazioneMedia(strutturaJSON.getDouble("valutazione_media"));
                                strutturaObject.setFasciaDiPrezzo(strutturaJSON.getString("fascia_di_prezzo"));
                                strutturaObject.setCategoria(strutturaJSON.getString("categoria"));
                                listaStrutture.add(strutturaObject);
                            }
                            if(listaStrutture.isEmpty())
                                nessunaStrutturaTrovata();
                            else mostraListaStrutture(listaStrutture);
                        }
                        catch(JSONException e){
                            Log.d(TAG, "onSuccess: FALLITO");
                        }
                    }



                    private void mostraListaStrutture(ArrayList<Struttura> strutture){
                        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                        transaction.replace(R.id.container, ListaStruttureFragment.newInstance(strutture), "ListStruttureFragment");
                        transaction.commit();
                    }

                }
        );



    }

    private void nessunaStrutturaTrovata() {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
        transaction.replace(R.id.container, NessunaStrrutturaTrovataFragment.newInstance(), "NessunaStrutturaTrovataFragment");
        transaction.commit();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setMap();
                //((MainActivity)getActivity()).toolbar.setVisibility(View.VISIBLE);
                //setLoginActionInDrawer();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                ((MainActivity)getActivity()).setMap();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
