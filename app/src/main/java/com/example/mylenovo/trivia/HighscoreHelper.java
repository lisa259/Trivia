package com.example.mylenovo.trivia;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HighscoreHelper implements Response.Listener<JSONArray>, Response.ErrorListener {

    private Context context;
    private HighscoreHelper.Callback activity;
    RequestQueue queue;
    Map<String, String> Data;

    // Method in interface are called, after current methods are done
    public interface Callback {
        void gotHighscores(JSONArray question);
        void gotHighscoresError(String message);
    }

    // Constructor
    public HighscoreHelper (Context inputContext) {
        this.context = inputContext;
    }

    // Get categories from url. Using interface Callback
    void getHighscore(HighscoreHelper.Callback inputActivity) {
        this.activity = inputActivity;
        queue = Volley.newRequestQueue(context);
        String url = "https://ide50-lisabeek.cs50.io:8080/list";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, this, this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // No scores found
        activity.gotHighscoresError(error.getMessage());
    }

    @Override
    public void onResponse(JSONArray response) {
        activity.gotHighscores(response);
    }


    public void postHighscore(final String name, final int score) {

        StringRequest postRequest = new StringRequest(Request.Method.POST,
                "https://ide50-lisabeek.cs50.io:8080/list",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                    }
                }
        ){  @Override
        protected Map<String,String> getParams() {
            Data = new HashMap<String, String>();
            Data.put("name", name);
            Data.put("score", String.valueOf(score));
            return Data;
        }
        };
        queue.add(postRequest);
    }

}
