package com.example.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import DAO.StrutturaDAO;
import DAO.VolleyCallback;
import model.Filtri;
import model.Struttura;


public class FiltriFragment extends Fragment {
    private static final String TAG = "FiltriFragment";


    private ImageButton backButton;
    private ViewGroup container;
    private Button cercaButton;
    private EditText nomeText;
    private EditText cittaText;
    private EditText distanzaText;
    private Spinner spinnerCategoria;
    private Spinner spinnerPrezzo;
    private Spinner spinnerValutazione;
    private Switch prossimitaSwitch;

    private MainActivity activity;  //TODO: Sostituire tutte le occorrenze di getActivity() o MainActivity... con activity

    private StrutturaDAO strutturaDAO;

    private double latitudine;
    private double longitudine;
    private FusedLocationProviderClient fusedLocationClient;

    public FiltriFragment() {

    }



    public static FiltriFragment newInstance() {
        return new FiltriFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.strutturaDAO = new StrutturaDAO(this.getActivity());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
    }


    private void setPosizione() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this.getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            latitudine = location.getLatitude();
                            longitudine = location.getLongitude();
                            System.out.println("Posizione ottenuta: " + latitudine + " " + longitudine);

                        } else {
                            latitudine = MainActivity.latitudine;
                            longitudine = MainActivity.longitudine;
                            Log.d(TAG, "location == null");
                        }
                    }
                });
    }


    private void initApiClient() {
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        showLocationSettingsDialog();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult result) {
                        Log.i(TAG, "onConnectionFailed() connectionResult = [" + result + "]");
                    }
                })
                .build();
        mGoogleApiClient.connect();
    }

    private void showLocationSettingsDialog() {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getActivity())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    setPosizione();
                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) e;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                /*resolvable.startResolutionForResult(
                                        getActivity(),
                                        MainActivity.REQUEST_CHECK_SETTINGS);

                                 */
                                startIntentSenderForResult(resolvable.getResolution().getIntentSender(), MainActivity.REQUEST_CHECK_SETTINGS, null, 0, 0, 0, null);
                            } catch (IntentSender.SendIntentException exception) {
                                // Ignore the error.
                            } catch (ClassCastException exception) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        System.out.println("richiesta: " + requestCode);
        if (requestCode == 214) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    //((MainActivity)getActivity()).setPosizionePerProssimita();
                    setPosizione();
                    break;
                case Activity.RESULT_CANCELED:
                    // The user was asked to change settings, but chose not to
                    disabilitaProssimita();
                    break;
                default:
                    break;
            }
        }
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
        prossimitaSwitch = view.findViewById(R.id.prossimitàSwitch);
        backButton = view.findViewById(R.id.backButtonSignup);

        initSpinners(view, adapter);

        configuraProssimitaSwitch();
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
    private void configuraProssimitaSwitch() {
        prossimitaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if (hasGPSPermissions()) {
                        initApiClient();
                        //setPosizione();
                        abilitaProssimita();
                    }
                    else{
                        askForGPSPermissions();
                        //setPosizione();
                    }
                }
                else{
                    disabilitaProssimita();
                }
            }
        });
    }

    private void askForGPSPermissions() {
        getActivity().requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION},
                2);
        //requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 2);
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("Ci sono");
        switch (requestCode){
            case 2:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    Log.d(TAG, "onRequestPermissionsResult: Abilitato");
                    initApiClient();
                    abilitaProssimità();
                }
                else{
                    Log.d(TAG, "onRequestPermissionsResult: disbilitato");
                    disabilitaProssimità();
                }
            }
        }
    }
    */

    void disabilitaProssimita() {
        distanzaText.setVisibility(View.INVISIBLE);
        cittaText.setVisibility(View.VISIBLE);
        prossimitaSwitch.setChecked(false);
    }


    void abilitaProssimita() {
        distanzaText.setVisibility(View.VISIBLE);
        cittaText.setVisibility(View.INVISIBLE);
        prossimitaSwitch.setChecked(true);
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
                System.out.println("Long e lat: " + MainActivity.longitudine + " " + MainActivity.latitudine);
                cercaStrutture();

            }
        });
        this.container = container;
        return view;
    }


    private void cercaStrutture(){
        String distanzaMassima;
        String citta;


        if(prossimitaSwitch.isChecked()) {
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
                                            prossimitaSwitch.isChecked()
                                            );

        strutturaDAO.getStrutturePerFiltri(filtriStruttura,
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
                                strutturaObject.setUrlFoto(strutturaJSON.getString("url_foto"));
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
        transaction.replace(R.id.container, NessunaStrutturaTrovataFragment.newInstance(), "NessunaStrutturaTrovataFragment");
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
