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
        System.out.println(givenPassword);
        return PasswordUtils.verifyUserPassword(givenPassword,correctPassword,salt);
    }

    public void tryLogin(String username, final String givenPassword, final VolleyCallback<Boolean> callback){
        RequestQueue queue = Volley.newRequestQueue(context);
        String queryRequestString = "https://m6o9t2bfx0.execute-api.eu-central-1.amazonaws.com/select/table?table=utente";
        queryRequestString +=appendRequestForLogin(username);
        System.out.println(queryRequestString);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, queryRequestString, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean loginSuccess;
                            if(response.length() == 0)
                                loginSuccess = false;
                            else
                                loginSuccess = checkPassword(response.getJSONObject(0), givenPassword);

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
