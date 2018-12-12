package com.example.mylenovo.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestHelpers implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private Callback activity;

    // Method in interface are called, after current methods are done
    public interface Callback {
        void gotQuestion(JSONArray question);
        void gotQuestionError(String message);
    }

    // Constructor
    public RequestHelpers (Context inputContext) {
        this.context = inputContext;
    }

    // Get categories from url. Using interface Callback
    void getQuestion(Callback inputActivity) {
        this.activity = inputActivity;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://opentdb.com/api.php?amount=10&difficulty=medium&type=multiple";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, this, this);
        queue.add(jsonObjectRequest);
    }


    @Override
    public void onResponse(JSONObject response) {
        Log.d("zoeken", "contact met questions");
        try {
            JSONArray array = response.getJSONArray("results");
            activity.gotQuestion(array);
        } catch (JSONException e) {
        e.printStackTrace();
        }
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        // No question found
        Log.d("zoeken", "geen contact met questions");
        activity.gotQuestionError(error.getMessage());
    }
}

