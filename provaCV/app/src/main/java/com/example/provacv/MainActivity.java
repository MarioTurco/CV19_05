package com.example.provacv;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonElement;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import DAO.StrutturaDAO;
import DAO.VolleyCallback;
import model.Struttura;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private final String TAG = "MainActivity";
    private FusedLocationProviderClient fusedLocationClient;

    private ImageButton filtriButton;

    private DrawerLayout drawerLayout;
    Toolbar toolbar;
    FloatingActionButton yourPositionButton;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    public static CustomSupportMapFragment mapFragment;
    private Menu menu;
    protected static Bundle instanceState;
    private StrutturaDAO strutturaDao;
    private MapboxMap mapboxMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDrawer();
        updateDrawer();
        setupButtons();
        updateDrawer();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        instanceState = savedInstanceState;
        strutturaDao = new StrutturaDAO(this); //TODO da sostituire eventualmente con un singleton
        setMap();

    }

    private void setUpBackPressed() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                finish();
                System.exit(0);
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void setupButtons(){
        setupFiltriButton();
        setUpBackPressed();
        setupYourPositionButton();
    }




    private void moveCamera(double latitude, double longitude){
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)) // Sets the new camera position
                .zoom(17) // Sets the zoom
                .bearing(180) // Rotate the camera
                .tilt(30) // Set the camera tilt
                .build(); // Creates a CameraPosition from the builder

        mapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 7000);
    }

    private void setupYourPositionButton(){
        yourPositionButton = findViewById(R.id.floatingActionButtonMap);
        yourPositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    moveCamera(location.getLatitude(), location.getLongitude());

                                    /*        .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                            .zoom(15)
                                            .build());
                                            */
                                }else{
                                    Toast.makeText(MainActivity.this, "Homepage selezionato", Toast.LENGTH_SHORT).show();
                                }
                                // Got last known location. In some rare situations this can be null.
                                /*if (location != null) {
                                    moveCamera(location.getLatitude(), location.getLongitude());
                                }else{
                                    Toast.makeText(MainActivity.this, "Homepage selezionato", Toast.LENGTH_SHORT).show();
                                }*/
                            }
                        });


            }
        });
    }

    private void setupFiltriButton() {
        filtriButton = findViewById(R.id.filtriButton);
        filtriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFiltriFragment();
            }
        });
    }

    private void loadFiltriFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
        transaction.replace(R.id.container, FiltriFragment.newInstance(), "filtriFragment");
        transaction.commit();
        toolbar.setVisibility(View.GONE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.login:
                loadLoginFragment();
                break;
            case R.id.signup:
                loadSignupFragment();
                break;
            case R.id.homepage:
                Toast.makeText(MainActivity.this, "Homepage selezionato", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                Toast.makeText(MainActivity.this, "Logout effettuato", Toast.LENGTH_SHORT).show();
                logout();
                updateDrawer();
        }
        return false;
    }

    private void logout() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean("isLogged", false).apply();
    }

    private void setupDrawer() {
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        menu = findViewById(R.id.drawerMenuGroup);
        navigationView = findViewById(R.id.navigationView);
        navigationView.getBackground().setAlpha(122);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        updateDrawer();
    }

    public void backToMainActivity() {
        setMap();
        updateDrawer();
    }

    private void updateDrawer() {
        menu = navigationView.getMenu();
        if (userIsLogged()) {
            Log.d(TAG, "onSharedPreferenceChanged: Mostra logout");
            menu.findItem(R.id.logout).setVisible(true);

            menu.findItem(R.id.login).setVisible(false);
            menu.findItem(R.id.signup).setVisible(false);

        } else {
            Log.d(TAG, "onSharedPreferenceChanged: Mostra login");
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.signup).setVisible(true);

            menu.findItem(R.id.logout).setVisible(false);
        }
    }


    private boolean userIsLogged() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.contains("isLogged"))
            return sharedPreferences.getBoolean("isLogged", false);
        return false;
    }


    private void loadSignupFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
        transaction.replace(R.id.container, SignupFragment.newInstance(), "signupFragment");
        transaction.commit();
        toolbar.setVisibility(View.GONE);
    }

    private void loadLoginFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
        transaction.replace(R.id.container, LoginFragment.newInstance(), "loginFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        toolbar.setVisibility(View.GONE);
    }



    protected void setMap() {
        Location lastKnownLocation;
        final MapboxMapOptions options = MapboxMapOptions.createFromAttributes(this, null);
        setPosition(options);
        if (!(hasGPSPermissions()))
            askForGPSPermissions();
        else {
            Log.d(TAG, "setMap: Abbiamo i permessi");
            //cambia le impostazioni della map box
        }
        Mapbox.getInstance(this, "pk.eyJ1IjoibWFyaW90dXJjbzQiLCJhIjoiY2s5NXZicG8zMG81aDNsbzFudmJtbXFvZCJ9.SAKPHTJnSi4BpAcRkBRclA");

        if (instanceState == null) {
            // Create fragment
            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
            // Build mapboxMap


            // Create map fragment
            mapFragment = CustomSupportMapFragment.newInstance(options, toolbar);
            // Add map fragment to parent container
            transaction.add(R.id.container, mapFragment, "com.mapbox.map");
            transaction.commit();
        } else {

            mapFragment = (CustomSupportMapFragment) getSupportFragmentManager().findFragmentByTag("com.mapbox.map");
        }

        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                    MainActivity.this.mapboxMap = mapboxMap;
                    mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {

                        private boolean pointClick(PointF point){
                            List<Feature> features = mapboxMap.queryRenderedFeatures(point, "cv19-map");

                            // Get the first feature within the list if one exist
                            if (features.size() > 0) {
                                Feature feature = features.get(0);

                                // Ensure the feature has properties defined
                                if (feature.properties() != null) {
                                    int idStruttura = 0;
                                    for (Map.Entry<String, JsonElement> entry : feature.properties().entrySet()) {
                                        if(entry.getKey().equals("id")) {
                                            idStruttura = Integer.parseInt(entry.getValue().toString());
                                            System.out.println(idStruttura);
                                        }
                                        Log.d(TAG, String.format("%s = %s", entry.getKey(), entry.getValue()));
                                    }
                                    mostraStrutturaDopoTap(idStruttura);
                                }
                            }
                            return true;
                        }

                        @Override
                        public boolean onMapClick(@NonNull LatLng point) {
                            return pointClick(mapboxMap.getProjection().toScreenLocation(point));
                        }
                    });
                    mapboxMap.getUiSettings().setCompassMargins(0, 120, 35, 0);
                    mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/marioturco4/ck95w1ltx0sdn1iqt1enmib6y"), new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            enableLocationComponent(style);
                        }
                    });
                }
            });
        }

    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (hasGPSPermissions()) {
            System.out.println("Ho i permessi");
            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();


            // Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            System.out.println("Non ho i permessi");
            askForGPSPermissions();
        }
    }

    private void mostraStrutturaFragment(Struttura struttura){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
        transaction.replace(R.id.container, DettagliStrutturaFragment.newInstance(struttura), "strutturaFragment");
        transaction.commit();
        toolbar.setVisibility(View.GONE);
    }

    private void mostraStrutturaDopoTap(int idStruttura){
        strutturaDao.getStrutturaById(idStruttura, new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject strutturaJSON){
                Struttura strutturaTappata = new Struttura();
                try {
                    strutturaTappata.setNome(strutturaJSON.getString("nome"));
                    strutturaTappata.setIndirizzo(strutturaJSON.getString("indirizzo"));
                    strutturaTappata.setLatitudine(strutturaJSON.getDouble("latitudine"));
                    strutturaTappata.setLongitudine(strutturaJSON.getDouble("longitudine"));
                    strutturaTappata.setDescrizione(strutturaJSON.getString("descrizione"));
                    strutturaTappata.setCittà(strutturaJSON.getString("citta"));
                    strutturaTappata.setIdStruttura(strutturaJSON.getInt("id_struttura"));
                    strutturaTappata.setValutazioneMedia(strutturaJSON.getDouble("valutazione_media"));
                    strutturaTappata.setFasciaDiPrezzo(strutturaJSON.getString("fascia_di_prezzo"));
                    strutturaTappata.setCategoria(strutturaJSON.getString("categoria"));
                    strutturaTappata.setUrlFoto(strutturaJSON.getString("url_foto"));

                    mostraStrutturaFragment(strutturaTappata);
                }
                catch(JSONException e){

                }
            }

            @Override
            public void onFail() {

            }
        });
    }

    private void setPosition(final MapboxMapOptions options) {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            options.camera(new CameraPosition.Builder()
                                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                    .zoom(15)
                                    .build());
                        }else{
                            Log.d(TAG, "onSuccess: Posizione default");
                            options.camera(new CameraPosition.Builder()
                                    .target(new LatLng(40.79444305, 14.46353868))
                                    .zoom(10)
                                    .build());
                        }
                    }
                });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private boolean hasFineLocationAccess() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasCoarseLocationAccess() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasGPSPermissions() {
        return hasFineLocationAccess() && hasCoarseLocationAccess();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: Abilitato");
                    fusedLocationClient.getLastLocation();

                } else {
                    Log.d(TAG, "onRequestPermissionsResult: disbilitato");
                    //TODO imposta telecamera mappa a delle cordinate prefissate (ad esempio l'italia)
                }
            }
        }
    }

    private void askForGPSPermissions() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }
}
