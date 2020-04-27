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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

    public FiltriFragment(SupportMapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }


    public static FiltriFragment newInstance(SupportMapFragment mapFragment) {
        FiltriFragment fragment = new FiltriFragment(mapFragment);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIOS_REQUEST_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIOS_REQUEST_LOCATION:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    abilitaProssimità();
                }
                else{
                    disabilitaProssimità();
                }
            }
        }
    }

    private void disabilitaProssimità() {
        distanzaText.setVisibility(View.INVISIBLE);
        cittaText.setVisibility(View.VISIBLE);
    }


    private void abilitaProssimità() {
        distanzaText.setVisibility(View.VISIBLE);
        cittaText.setVisibility(View.INVISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtri, container, false);
        initGUIElements(view);

        cercaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cercaStrutture();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, ListaStruttureFragment.newInstance(), "loginFragment");
                transaction.commit();
            }
        });
        this.container = container;
        return view;
    }

    private String addRequestParams(){
        String result = "";
        String nome = nomeText.getText().toString();

        if(!nome.equals(""))
            result += "&nome=" + nome;

        String citta = cittaText.getText().toString();
        if(!citta.equals(""))
            result += "&citta=" + citta;

        //TODO: da aggiungere distanza massima, prossimità

        String categoria = spinnerCategoria.getSelectedItem().toString();
        if(!categoria.equals("Nessuno"))
            result += "&categoria=" + categoria;

        String prezzo = spinnerPrezzo.getSelectedItem().toString();
        if(!prezzo.equals("Nessuno"))
            result += "&fascia_di_prezzo=" + prezzo;

        String valutazione = spinnerValutazione.getSelectedItem().toString();

        //Aggiungere criterio per valutazione media

        return result;
    }

    private void cercaStrutture(){
        RequestQueue queue = Volley.newRequestQueue(this.getActivity());
        String queryRequestString = "https://m6o9t2bfx0.execute-api.eu-central-1.amazonaws.com/test3/table?table=struttura";
        queryRequestString += addRequestParams();
        System.out.println(queryRequestString);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, queryRequestString, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Errore");

                    }
                });
        queue.add(jsonArrayRequest);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).toolbar.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).setMap(savedInstanceState);
                //setLoginActionInDrawer();
            }
        });
    }
}
