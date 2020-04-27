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

public class StrutturaDAO {
    private String TAG ="StrutturaDAO";
    private Context context;

    public StrutturaDAO(Context context){
        this.context = context;
    }



    public void strutturaQuery(Filtri filtriStruttura, final VolleyCallback<JSONArray> callback){
        RequestQueue queue = Volley.newRequestQueue(context);
        String queryRequestString = "https://m6o9t2bfx0.execute-api.eu-central-1.amazonaws.com/select/struttura?";
        queryRequestString += filtriStruttura.getNonNullStrings(2.000, 2.000);
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
