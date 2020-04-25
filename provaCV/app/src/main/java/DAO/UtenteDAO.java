package DAO;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.provacv.PasswordUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UtenteDAO {

    private Context context;

    public UtenteDAO(Context context){
        this.context=context;
    }

    private String appendRequestForLogin(String username){
        return "&nickname="+username;
    }

    private boolean checkPassword(JSONObject jsonObject, String givenPassword) throws JSONException{
        String correctPassword=null,salt=null;
        correctPassword = jsonObject.getString("password");
        salt = jsonObject.getString("salt");
        return PasswordUtils.verifyUserPassword(givenPassword,correctPassword,salt);
    }

    public boolean tryLogin(String username, final String givenPassword){
        RequestQueue queue = Volley.newRequestQueue(context);
        String queryRequestString = "https://m6o9t2bfx0.execute-api.eu-central-1.amazonaws.com/test3/table?table=utente";
        queryRequestString +=appendRequestForLogin(username);
        System.out.println(queryRequestString);
        final boolean [] loginResult = new boolean[1];
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, queryRequestString, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            loginResult[0] = checkPassword(response.getJSONObject(0), givenPassword);
                            System.out.println(loginResult[0]);
                        } catch (JSONException e) {}
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Errore");

                    }
                });
        queue.add(jsonArrayRequest);
        System.out.println("valora di ritorno"+ loginResult[0]);
        return loginResult[0];
    }
}
