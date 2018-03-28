package com.example.monic.searchwords;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WordsFound extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_found);

        Log.d("Inside", "activity 2");
        ArrayList<String> occurences = new ArrayList<String>();
        occurences = getIntent().getExtras().getStringArrayList("occurences");
        for (String word : occurences) {
            Log.d("words", word);
        }
    }
}
