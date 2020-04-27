package DAO;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.provacv.Filtri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StrutturaDAO {

    private Context context;

    public StrutturaDAO(Context context){
        this.context = context;
    }



    public JSONArray strutturaQuery(Filtri filtriStruttura){
        RequestQueue queue = Volley.newRequestQueue(context);
        String queryRequestString = "https://m6o9t2bfx0.execute-api.eu-central-1.amazonaws.com/select/table?table=struttura";
        queryRequestString += filtriStruttura.getNonNullStrings(2.000, 2.000);
        System.out.println(queryRequestString);
        final UtenteDAO.BooleanContainer booleanContainer = new UtenteDAO.BooleanContainer(false);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, queryRequestString, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean loginSuccess = checkPassword(response.getJSONObject(0), givenPassword);
                            callback.onSuccess(loginSuccess);
                        } catch (JSONException e) {}
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Errore");

                    }
                });
        queue.add(jsonArrayRequest);
    }
}
