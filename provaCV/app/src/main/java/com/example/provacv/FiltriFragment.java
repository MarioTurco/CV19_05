package com.example.provacv;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


public class FiltriFragment extends Fragment {
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

    private void setHomepageActionInDrawer(){
        Menu drawerMenu = ((MainActivity)getActivity()).navigationView.getMenu();
        drawerMenu.findItem(R.id.login).setVisible(false);
        drawerMenu.findItem(R.id.homepage).setVisible(true);
    }

    private void setLoginActionInDrawer(){
        Menu drawerMenu = ((MainActivity)getActivity()).navigationView.getMenu();
        drawerMenu.findItem(R.id.login).setVisible(true);
        drawerMenu.findItem(R.id.homepage).setVisible(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //setHomepageActionInDrawer();
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
        initSpinners(view, adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtri, container, false);
        initGUIElements(view);

        cercaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cercaStrutture();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, ListaStruttureActivity.newInstance(), "loginFragment");
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

        //da aggiungere: distanza massima, prossimità

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
        backButton = view.findViewById(R.id.backButtonSignup);
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
