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
    private EditText dataDiNascita;
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
        utenteDAO = new UtenteDAO(this.getActivity());
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupBackButton(savedInstanceState);
        inizializeUIElements(view);

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
                Utente utenteDaAggiungere = new Utente();
                utenteDaAggiungere.setEmail(emailEditText.getText().toString());
                utenteDaAggiungere.setMostraNickname(mostraNicknameCheckbox.isSelected());
                utenteDaAggiungere.setNickname(nicknameEditText.getText().toString());
                utenteDaAggiungere.setNome(nomeEditText.getText().toString());
                String dateToFormat = dataDiNascita.getText().toString();
                String salt = PasswordUtils.getSalt(30);
                String passwordCriptata = PasswordUtils.generateSecurePassword(PasswordEditText.getText().toString(),salt);
                utenteDaAggiungere.setPassword(passwordCriptata);
                utenteDaAggiungere.setSalt(salt);
                Date dataDiNascita = null;
                try {
                    dataDiNascita = new SimpleDateFormat("yyyy-MM-dd").parse(dateToFormat);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                utenteDaAggiungere.setDataDiNascita(dataDiNascita);
                utenteDAO.registraUtente(utenteDaAggiungere, new VolleyCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        if (result){
                            Toast.makeText(getContext(),"Registrato con successo", Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(getContext(),"Registrazione Fallita", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFail() {
                        Log.d(TAG,"Registrazione fallita errore volley");
                    }
                });
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

    public class DatePickerFragment extends DialogFragment
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
            dataDiNascita.setText(day + "/" + month + "/" + year);

        }
    }

}
