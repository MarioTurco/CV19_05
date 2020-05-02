package com.example.provacv;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import DAO.UtenteDAO;
import DAO.VolleyCallback;
import model.Utente;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {
    private ImageButton backButtonSignup;
    private static EditText dataDiNascita;
    private Button registrazioneButton;
    private UtenteDAO utenteDAO;
    private EditText emailEditText;
    private EditText PasswordEditText;
    private EditText nomeEditText;
    private EditText cognomeEditText;
    private EditText nicknameEditText;
    private CheckBox mostraNicknameCheckbox;
    private String TAG="Signup Fragment";

    public SignupFragment(){

    }

    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    private void inizializeUIElements(View view){
        dataDiNascita = view.findViewById(R.id.dateEditText);
        registrazioneButton = view.findViewById(R.id.registrazioneButton);
        backButtonSignup = view.findViewById(R.id.backButtonSignup);
        emailEditText = view.findViewById(R.id.emailEditText);
        PasswordEditText = view.findViewById(R.id.PasswordEditText);
        nomeEditText = view.findViewById(R.id.nomeEditText);
        cognomeEditText = view.findViewById(R.id.cognomeEditText);
        nicknameEditText = view.findViewById(R.id.nicknameEditText);
        mostraNicknameCheckbox = view.findViewById(R.id.mostraNicknameCheckbox);
    }

    private boolean checkCampiNonVuoti(){
        if(emailEditText.getText().toString().equals("") ||
                nicknameEditText.getText().toString().equals("") ||
                nomeEditText.getText().toString().equals("") ||
                cognomeEditText.getText().toString().equals("") ||
                PasswordEditText.getText().toString().equals("") ||
                dataDiNascita.getText().toString().equals(""))
            return false;
        else return true;
    }

    private boolean checkLunghezzaPassword(){
        if(PasswordEditText.getText().toString().length() < 5)
            return false;
        else return true;
    }

    private Utente creaUtenteDaInserire(){
        Utente utenteDaAggiungere = new Utente();
        if(checkCampiNonVuoti()) {
            if(checkLunghezzaPassword()) {
                utenteDaAggiungere.setEmail(emailEditText.getText().toString());
                utenteDaAggiungere.setMostraNickname(mostraNicknameCheckbox.isSelected());
                utenteDaAggiungere.setNickname(nicknameEditText.getText().toString());
                utenteDaAggiungere.setNome(nomeEditText.getText().toString() + " " + cognomeEditText.getText().toString());
                String salt = PasswordUtils.getSalt(30);
                String passwordCriptata = null;
                passwordCriptata = PasswordUtils.generateSecurePassword(PasswordEditText.getText().toString(), salt);
                utenteDaAggiungere.setPassword(passwordCriptata);
                utenteDaAggiungere.setSalt(salt);
                utenteDaAggiungere.setDataDiNascita(dataDiNascita.getText().toString());
            }
            else throw new IllegalArgumentException("La password deve essere di almeno 5 caratteri!");
        }
        else throw new IllegalArgumentException("Compila tutti i campi");
        return utenteDaAggiungere;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inizializeUIElements(view);
        setupBackButton(savedInstanceState);
        utenteDAO = new UtenteDAO(this.getActivity());
        dataDiNascita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        registrazioneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Utente utenteDaAggiungere = creaUtenteDaInserire();
                    utenteDAO.registraUtente(utenteDaAggiungere, new VolleyCallback<String>() {

                        private String errorMessage(String result){
                            if(result.contains("Email"))
                                return ": Email non valida!";
                            if(result.contains("utente_pkey"))
                                return ": Nickname già in uso!";
                            if(result.contains("unique_email"))
                                return ": Email già in uso!";
                            return "";
                        }
                        @Override
                        public void onSuccess(String result) {
                            if (result.contains("successfully")){
                                Toast.makeText(getContext(),"Registrato con successo", Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(getContext(),"Registrazione Fallita" + errorMessage(result), Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onFail() {
                            Toast.makeText(getContext(),"Errore di connessione", Toast.LENGTH_LONG).show();
                            Log.d(TAG,"Registrazione fallita errore volley");
                        }
                    });
                }
                catch(IllegalArgumentException e){
                    Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                ((MainActivity)getActivity()).toolbar.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).setMap();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    private void setupBackButton(final Bundle savedInstanceState) {

        backButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity)getActivity()).toolbar.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).setMap();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                ((MainActivity) getActivity()).setMap();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            dataDiNascita.setText(day + "/" + month+1 + "/" + year);

        }
    }

}
