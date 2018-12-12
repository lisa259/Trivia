package com.example.mylenovo.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class TriviaActivity extends AppCompatActivity implements RequestHelpers.Callback{

    TextView tv_Number;
    TextView tv_Category;
    TextView tv_Question;
    Button btn_0;
    Button btn_1;
    Button btn_2;
    Button btn_3;

    int numberOfQuestion = 1;
    int amountOfQuestions = 10;
    int score = 0;
    String nameIntent;
    JSONArray questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        tv_Number = findViewById(R.id.tv_Number);
        tv_Category = findViewById(R.id.tv_Category);
        tv_Question = findViewById(R.id.tv_Question);
        btn_0 = findViewById(R.id.btn_0);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);

        Intent intent = getIntent();
        nameIntent = intent.getStringExtra("name");

        RequestHelpers request = new RequestHelpers(this);
        request.getQuestion(this);
    }

    @Override
    public void gotQuestion(JSONArray aQuestions) {
        this.questions = aQuestions;
        loadQuestion();
    }

    @Override
    public void gotQuestionError(String message) {
        // No questions found
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void AnswerClicked (View view) {
        Button button = (Button) view;
        try {
            JSONObject object = questions.getJSONObject(numberOfQuestion-1);
            String correct = object.getString("correct_answer");
            if (correct == button.getText().toString()) {
                score++;
            }

            numberOfQuestion++;
            if (numberOfQuestion <= amountOfQuestions) {
                loadQuestion();
            } else {
                // Game if over, go to highscore screen
                Intent intent = new Intent(TriviaActivity.this, HighscoreActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("name", nameIntent);
                startActivity(intent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void loadQuestion() {
        try {
            JSONObject object = questions.getJSONObject(numberOfQuestion-1);
            String category = object.getString("category");
            String question = object.getString("question");

//            question = URLDecoder.decode(question, "UTF-8");

            ArrayList<String> answers = new ArrayList<>();
            answers.add(object.getString("correct_answer"));

            for (int j = 0; j < object.getJSONArray("incorrect_answers").length(); j++){
                answers.add(object.getJSONArray("incorrect_answers").getString(j));
            }
            Collections.shuffle(answers);

            tv_Number.setText("Question: " + String.valueOf(numberOfQuestion + "/10"));
            tv_Category.setText("Category: " + category);
            tv_Question.setText(question);

            btn_0.setText(answers.get(0));
            btn_1.setText(answers.get(1));
            btn_2.setText(answers.get(2));
            btn_3.setText(answers.get(3));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Safe current state of game in outstate
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState); // always call super
        outState.putInt("numberOfQuestion", numberOfQuestion);
        outState.putInt("score", score);
    }

    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        score = inState.getInt("score");
        numberOfQuestion = inState.getInt("numberOfQuestion");
    }
}
