package com.example.provacv;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import DAO.UtenteDAO;
import DAO.VolleyCallback;


public class LoginFragment extends Fragment {
    ImageButton backButtonLogin;
    Button loginButton;
    UtenteDAO utenteDAO;
    EditText usernameLoginText;
    EditText passwordLoginText;
    TextView registratiTextLink;
    ProgressBar progressBar;

    private String TAG = "LoginFragment";

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.utenteDAO = new UtenteDAO(this.getActivity());
    }

    private void initViewElements(View view) {
        backButtonLogin = view.findViewById(R.id.backButtonLogin);
        loginButton = view.findViewById(R.id.loginButton);
        usernameLoginText = view.findViewById(R.id.usernameLoginText);
        passwordLoginText = view.findViewById(R.id.passwordLoginText);
        registratiTextLink = view.findViewById(R.id.registratiTextLink);
        progressBar = view.findViewById(R.id.progressBar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViewElements(view);

        setupBackButton(savedInstanceState);


        registratiTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                transaction.replace(R.id.container, SignupFragment.newInstance(), "filtriFragment");
                transaction.commit();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: cliccato");
                progressBar.setVisibility(View.VISIBLE);
                checkCredenziali();
                progressBar.setVisibility(View.GONE);
            }
        });
        return view;
    }

    private void setupBackButton(final Bundle savedInstanceState) {
        backButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).setMap(savedInstanceState);
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                ((MainActivity) getActivity()).setMap(savedInstanceState);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void checkCredenziali() {
        String username = String.valueOf(usernameLoginText.getText());
        String password = String.valueOf(passwordLoginText.getText());
        utenteDAO.tryLogin(username, password,
                new VolleyCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        if (result) {
                            Log.d(TAG, "onSuccess: Login effettuato");
                            changeUserStatus(true);
                            backButtonLogin.performClick();
                        } else
                            Log.d(TAG, "onSuccess: Login fallito");
                            usernameLoginText.setBackgroundColor(Color.parseColor("#DD2020"));
                            passwordLoginText.setBackgroundColor(Color.parseColor("#DD2020"));
                        Toast.makeText(getContext(), "Login fallito", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail() {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                        transaction.add(R.id.container, ConnessioneAssenteFragment.newInstance(), "Connessione Assente");
                        transaction.commit();
                    }
                });


    }

    private void
    changeUserStatus(boolean newStatus) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences.edit().putBoolean("isLogged", newStatus).apply();
        Log.d("LOGINFRAG", "changeUserStatus: cambiato ");
    }
}
