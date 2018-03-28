package com.example.monic.searchwords;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout.LayoutParams params;
    LinearLayout linearLayout;
    private int counter = 0;
    private boolean caseCheck;

    ArrayList<String> resultstrings;
    ArrayList<String> searchwords = new ArrayList<String>();
    HashMap<Integer,String> items = new HashMap<Integer,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.INVISIBLE);
        linearLayout = (LinearLayout)findViewById(R.id.scrollViewLinear);
        linearLayout.removeAllViews();
        EditText word = new EditText(this);
        word.setLayoutParams(new LinearLayout.LayoutParams(650, LinearLayout.LayoutParams.WRAP_CONTENT));
        counter++;
        word.setId(counter);
        word.setSingleLine();
        word.requestFocus();
        word.setTag("addtext"+String.valueOf(counter));
        //searchwords.add(word.getId(),word.getText().toString());
        Log.d("array",String.valueOf(word.getId()));
        items.put(word.getId(),"addtext"+word.getId());
        linearLayout.addView(word);
        counter++;
        Log.d("I am here", "added text view");

        ImageView addbtn= new ImageView(MainActivity.this);
        addbtn.setOnClickListener(this);
        addbtn.setId(counter);
        addbtn.setTag("addbtn"+String.valueOf(counter));
        addbtn.setImageResource(R.drawable.add);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(120, 120);
        param.setMargins(1000, Integer.parseInt(String.valueOf(-90)), 0, 0);
        addbtn.setLayoutParams(param);
        Log.d("I am here", "after");
        linearLayout.addView(addbtn);
        Log.d("I am here", "after");
        items.put(addbtn.getId(),"addbtn"+addbtn.getId());
        Log.d("I am here", "added");

        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CheckBox matchCase= (CheckBox) findViewById(R.id.checkMatchCases);
                if(matchCase.isChecked())
                    caseCheck=true;
                else
                    caseCheck=false;

                for(Map.Entry<Integer,String> entry:items.entrySet()) {
                    if ((entry.getValue()).equals("addtext"+entry.getKey()))
                    {
                        EditText word1 = (EditText) findViewById(entry.getKey());
                        if(!(word1.getText().toString().equals("") || word1.getText().toString().contains(" "))) {
                            if(searchwords.contains(word1.getText().toString())){
                                Toast.makeText(getApplicationContext(),"Duplicate words Exits",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else
                                searchwords.add(word1.getText().toString());
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                new AsyncGenerator().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }

    @Override
    public void onClick(View v) {
        linearLayout = (LinearLayout) findViewById(R.id.scrollViewLinear);
        if((v.getTag().toString()).equals("addbtn"+v.getId())) {

            linearLayout.removeView(v);
            ImageView delbtn = new ImageView(this);
            LinearLayout.LayoutParams paramsRemove = new LinearLayout.LayoutParams(120, 120);
            paramsRemove.setMargins(650, Integer.parseInt(String.valueOf(-80)), 0, 0);
            delbtn.setLayoutParams(paramsRemove);
            delbtn.setImageResource(R.drawable.remove);
            delbtn.setTag("delbtn" + String.valueOf(counter));
            delbtn.setId(v.getId());
            linearLayout.addView(delbtn);
            delbtn.setOnClickListener(this);
            items.put(delbtn.getId(), "delbtn" + delbtn.getId());
            Log.d("id",String.valueOf(delbtn.getId()));
            counter++;
            EditText et = new EditText(this);
            et.setText("");

            et.setLayoutParams(new LinearLayout.LayoutParams(650, LinearLayout.LayoutParams.WRAP_CONTENT));
            et.setTag("addtext" + String.valueOf(counter));
            et.setId(counter);
            et.setSingleLine();
            et.requestFocus();
            Log.d("demo", String.valueOf(et.getId()));
            linearLayout.addView(et);
            items.put(et.getId(), "addtext" + et.getId());
            counter++;

            ImageView addbtn = new ImageView(this);
            addbtn.setImageResource(R.drawable.add);
            addbtn.setTag("addbtn" + String.valueOf(counter));
            addbtn.setId(counter);
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(120, 120);
            buttonLayoutParams.setMargins(650, Integer.parseInt(String.valueOf(-80)), 0, 0);
            addbtn.setLayoutParams(buttonLayoutParams);
            linearLayout.addView(addbtn);
            addbtn.setOnClickListener(this);
            items.put(addbtn.getId(), "addbtn" + addbtn.getId());


        }
        else if((v.getTag().toString()).equals("delbtn"+v.getId()))
        {
            Log.d("demo","in del case");
            linearLayout.removeView(v);
            linearLayout.removeView(findViewById(v.getId()-1));
            items.remove(v.getId());
            items.remove(v.getId()-1);
        }
    }

    class AsyncGenerator extends AsyncTask<Void, Integer, Void> {

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(100);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            Intent i = new Intent(MainActivity.this , WordsFound.class);
            i.putStringArrayListExtra("occurences", resultstrings);
            startActivity(i);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... params) {
            resultstrings = new ArrayList<String>();

            try {
                for (int i = 0; i < searchwords.size(); i++) {
                    Log.d("Searching me",searchwords.get(i));
                    ArrayList interval = new ArrayList<String>();
                    SearchWord searching = new SearchWord(getApplicationContext());
                    interval = searching.searchWords(searchwords.get(i),caseCheck);
                    resultstrings.addAll(interval);
                    publishProgress((int)(i+1 * (100 /searchwords.size())));
                    Log.d("progress ", "is " + (int)(i+1 * (100 /searchwords.size())));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}