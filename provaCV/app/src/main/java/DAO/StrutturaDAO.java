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
import com.example.provacv.Filtri;
import com.example.provacv.MainActivity;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StrutturaDAO {
    private String TAG ="StrutturaDAO";
    private Context context;

    public StrutturaDAO(Context context){
        this.context = context;
    }

    public void getStrutturaById(int idStruttura, final VolleyCallback<JSONObject> callback){
        RequestQueue queue = Volley.newRequestQueue(context);
        String queryString = "https://m6o9t2bfx0.execute-api.eu-central-1.amazonaws.com/select/table?table=struttura&id_struttura="+idStruttura;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, queryString, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    callback.onSuccess(response.getJSONObject(0));
                }
                catch(JSONException e){
                    //TODO JSONException
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail();
                Log.d(TAG, "ERRORE VOLLEY");
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    public void strutturaQuery(Filtri filtriStruttura, final VolleyCallback<JSONArray> callback){
        RequestQueue queue = Volley.newRequestQueue(context);
        String queryRequestString = "https://m6o9t2bfx0.execute-api.eu-central-1.amazonaws.com/select/struttura?";
        queryRequestString += filtriStruttura.getNonNullStrings(MainActivity.longitudine, MainActivity.latitudine);
        System.out.println(queryRequestString);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, queryRequestString, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                            callback.onSuccess(response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                       callback.onFail();

                    }
                });
        queue.add(jsonArrayRequest);
    }
}
