package com.example.provacv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import DAO.UtenteDAO;


public class LoginFragment extends Fragment {
    ImageButton backButtonLogin;
    Button loginButton;
    UtenteDAO utenteDAO;
    EditText usernameLoginText;
    EditText passwordLoginText;
    TextView registratiTextLink;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.utenteDAO = new UtenteDAO(this.getActivity());
    }

    private void initViewElements(View view){
        backButtonLogin = view.findViewById(R.id.backButtonLogin);
        loginButton = view.findViewById(R.id.loginButton);
        usernameLoginText = view.findViewById(R.id.usernameLoginText);
        passwordLoginText = view.findViewById(R.id.passwordLoginText);
        registratiTextLink = view.findViewById(R.id.registratiTextLink);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViewElements(view);
        backButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).toolbar.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).setMap(savedInstanceState);
            }
        });

        registratiTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, SignupFragment.newInstance(), "filtriFragment");
                transaction.commit();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredenziali();
            }
        });
        return view;
    }

    private void checkCredenziali(){
        String username = String.valueOf(usernameLoginText.getText());
        String password = String.valueOf(passwordLoginText.getText());
        if(this.utenteDAO.tryLogin(username, password)){
            System.out.println("HAFUNZIONATO");
        }
        else System.out.println("NON HAFUNZIONATO");
    }
}
