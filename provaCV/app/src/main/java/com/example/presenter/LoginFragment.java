package com.example.presenter;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
    private ImageButton bottoneIndietro;
    private Button bottoneLogin;
    private UtenteDAO utenteDAO;
    private EditText usernameLoginText;
    private EditText passwordLoginText;
    private TextView registratiTextLink;
    private ProgressBar progressBar;
    private View view;
    private MainActivity mainActivity;

    public LoginFragment(){

    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.utenteDAO = new UtenteDAO(this.getActivity());
        mainActivity = (MainActivity)getActivity();
        if(mainActivity == null)
            System.out.println("è null");
    }

    private void referenziaElementiGUI(View view) {
        bottoneIndietro = view.findViewById(R.id.backButtonLogin);
        bottoneLogin = view.findViewById(R.id.loginButton);
        usernameLoginText = view.findViewById(R.id.usernameLoginText);
        passwordLoginText = view.findViewById(R.id.passwordLoginText);
        registratiTextLink = view.findViewById(R.id.registratiTextLink);
        progressBar = view.findViewById(R.id.progressBar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        referenziaElementiGUI(view);

        setupBackButton(savedInstanceState);


        registratiTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                transaction.replace(R.id.container, RegistrazioneFragment.newInstance(), "filtriFragment");
                transaction.commit();
            }
        });

        bottoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LoginFragment", "onClick: cliccato");
                progressBar.setVisibility(View.VISIBLE);
                checkCredenziali();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }

    private void setupBackButton(final Bundle savedInstanceState) {
        bottoneIndietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);

                mainActivity.tornaAllaMainActivity();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {

            @Override
            public void handleOnBackPressed() {
                mainActivity.tornaAllaMainActivity();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void checkCredenziali() {
        final String username = String.valueOf(usernameLoginText.getText());
        String password = String.valueOf(passwordLoginText.getText());
        utenteDAO.effettuaLogin(username, password,
                new VolleyCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        if (result) {
                            Log.d("LoginFragment", "onSuccess: Login effettuato");
                            changeUserStatus(true);
                            updateNickname(username);
                            bottoneIndietro.performClick();
                        } else {
                            Log.d("LoginFragment", "onSuccess: Login fallito");
                            usernameLoginText.getBackground().mutate().setColorFilter(Color.parseColor("#DD2020"), PorterDuff.Mode.SRC_ATOP);
                            passwordLoginText.getBackground().mutate().setColorFilter(Color.parseColor("#DD2020"), PorterDuff.Mode.SRC_ATOP);
                            Toast.makeText(getContext(), "Login fallito", Toast.LENGTH_SHORT).show();
                        }
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

    private void updateNickname(String nickname) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences.edit().putString("nickname", nickname).apply();
        Log.d("LOGINFRAG", "changeUsername: cambiato " + nickname);
    }

    private void
    changeUserStatus(boolean newStatus) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences.edit().putBoolean("isLogged", newStatus).apply();
        Log.d("LOGINFRAG", "changeUserStatus: cambiato ");
    }
}
