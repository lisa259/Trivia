package com.example.mylenovo.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HighscoreActivity extends AppCompatActivity implements HighscoreHelper.Callback{

    Integer score;
    String name;
    HighscoreHelper request;
    Boolean Added = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 100);
        name = intent.getStringExtra("name");

        Log.d("zoeken", "begin");

        request = new HighscoreHelper(this);
        request.getHighscore(this);
    }

    @Override
    public void gotHighscores(JSONArray scores) {
        // Checken of nieuwe score hoog genoeg is voor in highscore lijst\
        if (score != 100) {
            if (Added == false) {
                Log.d("zoeken", "addd " + name);
                Added = true;
                request.postHighscore(name, score);
                request.getHighscore(this);
            }
        }

        ArrayList<HighscoreItem> highscores = new ArrayList<>();
        for (int i = 0; i < scores.length(); i++){
            try {
                // Maak van alles een object
                JSONObject item = scores.getJSONObject(i);
                highscores.add(new HighscoreItem(item.getString("name"), item.getString("score")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Log.d("zoeken", highscores.get(highscores.size()-1).getName());
        HighscoreAdapter arrayAdapter = new HighscoreAdapter(this, R.layout.highscore_item, highscores);
        ListView listView = findViewById(R.id.lv_items);
        listView.setAdapter(arrayAdapter);
        Log.d("zoeken", "check");
    }

    @Override
    public void gotHighscoresError(String message){
        // No scoress found
        Log.d("zoeken", "gaat niet goed");
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void StartQuiz(View view) {
        EditText et_Name = findViewById(R.id.et_Name);
        String name = et_Name.getText().toString();
        Intent intent = new Intent(HighscoreActivity.this, TriviaActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}
