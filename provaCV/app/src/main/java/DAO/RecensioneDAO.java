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

import model.Recensione;

public class RecensioneDAO {
    private Context context;
    private final String TAG = "RecensioneDAO";
    public RecensioneDAO(Context context) {
        this.context = context;
    }

    public void getRecensioniByIdStruttura(int idStruttura, final VolleyCallback<JSONArray> callback){
        RequestQueue queue = Volley.newRequestQueue(context);
        String queryRequestString = "https://m6o9t2bfx0.execute-api.eu-central-1.amazonaws.com/select/table?table=RECENSIONE LEFT OUTER JOIN UTENTE ON AUTORE=NICKNAME&struttura=" + idStruttura + "&stato_recensione=Approvata";
        Log.d(TAG, "getRecensioniByIdStruttura: " + queryRequestString);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, queryRequestString, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Errore");

                    }
                });
        queue.add(jsonArrayRequest);
    }
    public void aggiungiRecensione(Recensione nuovaRecensione, final VolleyCallback<Boolean> callback){
        RequestQueue queue = Volley.newRequestQueue(context);
        String queryRequestString = "https://m6o9t2bfx0.execute-api.eu-central-1.amazonaws.com/insert/insertrecensione?testo=" + nuovaRecensione.getTesto() + "&datarecensione=" + nuovaRecensione.getDataRecensione()
                +"&titolo=" + nuovaRecensione.getTitolo() +"&valutazione=" + nuovaRecensione.getValutazione() + "&struttura=" + nuovaRecensione.getStruttura() +"&autore=" + nuovaRecensione.getAutore();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest
                (Request.Method.GET, queryRequestString, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean addSuccess;
                            if(response.length() == 0)
                                addSuccess = false;
                            else
                                addSuccess = true;
                            callback.onSuccess(addSuccess);
                        } catch (Exception e) {}
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFail();
                        Log.d(TAG, "onErrorResponse: Errore" );
                        error.printStackTrace();
                    }
                });
        queue.add(jsonArrayRequest);
    }
}
