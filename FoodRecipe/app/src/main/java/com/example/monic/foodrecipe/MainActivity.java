package com.example.monic.foodrecipe;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements FloatingActionButton.OnClickListener {
    LinearLayout linearLayout;
    private int counter=0;
    HashMap<Integer,String> items=new HashMap<Integer,String>();
    final static String Key="";
    final static String base_Url=" http://www.recipepuppy.com/api/?format=xml&i=";
    ArrayList<String> ingredients=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searching = (Button)findViewById(R.id.button1);
        final EditText dishname = (EditText)findViewById(R.id.editdish);

        linearLayout = (LinearLayout)findViewById(R.id.scrollViewLinear);
        linearLayout.removeAllViews();

        EditText word = new EditText(this);
        word.setLayoutParams(new LinearLayout.LayoutParams(650, LinearLayout.LayoutParams.WRAP_CONTENT));
        counter++;
        word.setId(counter);
        word.setSingleLine();
        word.requestFocus();
        word.setTag("addtext"+String.valueOf(counter));
        items.put(word.getId(),"addtext"+word.getId());
        linearLayout.addView(word);
        counter++;

        FloatingActionButton addbtn2= new FloatingActionButton(MainActivity.this);
        addbtn2.setOnClickListener(this);
        addbtn2.setId(counter);
        addbtn2.setTag("addbtn"+String.valueOf(counter));
        addbtn2.setImageResource(R.drawable.add);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(120, 120);
        param.setMargins(650, Integer.parseInt(String.valueOf(-90)), 0, 0);
        addbtn2.setSize(FloatingActionButton.SIZE_MINI);
        addbtn2.setLayoutParams(param);
        linearLayout.addView(addbtn2);
        items.put(addbtn2.getId(),"addbtn"+addbtn2.getId());

        searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(Map.Entry<Integer,String> entry:items.entrySet()) {
                    if ((entry.getValue()).equals("addtext"+entry.getKey()))
                    {
                        EditText word1 = (EditText) findViewById(entry.getKey());
                        if(!(word1.getText().toString().equals("") || word1.getText().toString().contains(" "))) {

                            ingredients.add(word1.getText().toString());
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                int size = ingredients.size();

                RequestParam params = new RequestParam(base_Url);
                for(int i=0;i<size;i++) {
                    params.addParams("Ingredients" + i, ingredients.get(i));
                }
                params.addParams("Dish",dishname.getText().toString());

                Intent i = new Intent(MainActivity.this, Recipes.class);
                i.putExtra("url", params.getEncodedUrl());
                startActivity(i);
            }
        });

    }

    @Override
    public void onClick(View v) {
        linearLayout = (LinearLayout) findViewById(R.id.scrollViewLinear);
        if ((v.getTag().toString()).equals("addbtn" + v.getId())) {
            if(linearLayout.getChildCount() > 8) {
                Toast.makeText(MainActivity.this, "Maximum only five ingredients", Toast.LENGTH_SHORT).show();
            } else {
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
                counter++;
                EditText et = new EditText(this);
                et.setText("");

                et.setLayoutParams(new LinearLayout.LayoutParams(650, LinearLayout.LayoutParams.WRAP_CONTENT));
                et.setTag("addtext" + String.valueOf(counter));
                et.setId(counter);
                et.setSingleLine();
                et.requestFocus();
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
        }

        if((v.getTag().toString()).equals("delbtn"+v.getId()))
        {
            linearLayout.removeView(v);
            linearLayout.removeView(findViewById(v.getId()-1));
            items.remove(v.getId());
            items.remove(v.getId()-1);
        }
    }
}
