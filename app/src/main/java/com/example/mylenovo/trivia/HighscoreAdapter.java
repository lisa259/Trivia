package com.example.mylenovo.trivia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighscoreAdapter extends ArrayAdapter<HighscoreItem> {

    private Context context;

    public HighscoreAdapter(Context context, int resource, ArrayList<HighscoreItem> highscores) {
        super(context, resource, highscores);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;
        HighscoreItem highscore = getItem(position);

        // If there is no View, inflate one with customized XML
        if (result== null) {
            result= LayoutInflater.from(context).inflate(R.layout.highscore_item,parent,false);
        }

        // Set the strings of the textviews
        TextView name = result.findViewById(R.id.tv_Name);
        TextView score = result.findViewById(R.id.tv_Score);

        name.setText(highscore.getName());
        score.setText(String.valueOf(highscore.getScore()));

        return result;
    }
}
