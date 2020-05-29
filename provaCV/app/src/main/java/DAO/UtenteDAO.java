package DAO;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import model.Utente;
import utils.PasswordUtils;

import java.net.URLEncoder;

public class UtenteDAO {

    private Context context;

    public UtenteDAO(Context context) {
        this.context = context;
    }

    private String aggiungiUsernameAllaQuery(String username) {
        return "&nickname=" + username;
    }

    private boolean controllaPassword(JSONObject utenteJson, String givenPassword) throws JSONException {
        String correctPassword = null, salt = null;
        correctPassword = utenteJson.getString("password");
        salt = utenteJson.getString("salt");
        System.out.println(givenPassword + " " + correctPassword + " " + salt);
        return PasswordUtils.verifyUserPassword(givenPassword, correctPassword, salt);
    }

    public void effettuaLogin(String username, final String givenPassword, final VolleyCallback<Boolean> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String queryRequestString = "https://m6o9t2bfx0.execute-api.eu-central-1.amazonaws.com/select/table?table=utente";
        queryRequestString += aggiungiUsernameAllaQuery(username);
        System.out.println(queryRequestString);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, queryRequestString, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean loginSuccess;
                            if (response.length() == 0) {
                                System.out.println("Fallito");
                                loginSuccess = false;
                            }
                            else
                                loginSuccess = controllaPassword(response.getJSONObject(0), givenPassword);

                            callback.onSuccess(loginSuccess);
                        } catch (JSONException e) {
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("UtenteDAO", "onErrorResponse: Errore");
                    }
                });
        queue.add(jsonArrayRequest);
    }

    private String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    public void isAccountEsistente(String username, final VolleyCallback<Boolean> callback){
        RequestQueue queue = Volley.newRequestQueue(context);
        String queryRequestString = "https://m6o9t2bfx0.execute-api.eu-central-1.amazonaws.com/select/table?table=utente";
        queryRequestString += aggiungiUsernameAllaQuery(username);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, queryRequestString, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                            callback.onSuccess(response.length() != 0);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("UtenteDAO", "onErrorResponse: Errore");
                    }
                });
        queue.add(jsonArrayRequest);
    }

    private String costruisciQueryInserimento(Utente utente) {
        String queryRequestString = "https://m6o9t2bfx0.execute-api.eu-central-1.amazonaws.com/insert/utente?";
        queryRequestString += "nome=" + utente.getNome();
        queryRequestString += "&nickname=" + utente.getNickname();
        queryRequestString += "&email=" + utente.getEmail();
        queryRequestString += "&data_di_nascita=" + utente.getDataDiNascita();
        queryRequestString += "&recensioniapprovate=0&recensionirifiutate=0";
        queryRequestString += "&password=" + encodeValue(utente.getPassword());
        queryRequestString += "&salt=" + utente.getSalt();
        queryRequestString += "&mostra_nickname=" + utente.isMostraNickname();
        return queryRequestString;
    }

    public void registraUtente(Utente utente, final VolleyCallback<String> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String queryRequestString = costruisciQueryInserimento(utente);
        System.out.println(queryRequestString);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest
                (Request.Method.GET, queryRequestString, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String addSuccess = response.getString("status");
                            callback.onSuccess(addSuccess);
                        } catch (Exception e) {
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFail();
                        Log.d("UtenteDAO", "onErrorResponse: Errore");
                        error.printStackTrace();
                    }
                });
        queue.add(jsonArrayRequest);

    }
}
