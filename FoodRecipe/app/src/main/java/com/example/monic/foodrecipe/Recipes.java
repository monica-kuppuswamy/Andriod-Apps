package com.example.monic.foodrecipe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Recipes extends AppCompatActivity implements GetRecipeAsyncTask.IData, GetImageAsyncTask.IImage{

    int currentPosition;
    final String imageUrl = "https://c1.staticflickr.com/5/4286/35513985750_2690303c8b_z.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        String url = getIntent().getExtras().getString("url");
        new GetRecipeAsyncTask(Recipes.this).execute(url);
    }

    @Override
    public void setupData(ArrayList<Recipe> s) {

        currentPosition = 0;
        final ArrayList<Recipe> result = s;
        final TextView title = (TextView) findViewById(R.id.description);
        final TextView ing = (TextView) findViewById(R.id.details);
        final TextView urlx = (TextView) findViewById(R.id.urllink);
        final Button finish = (Button) findViewById(R.id.finish);

        title.setText(s.get(0).getTitle());
        ing.setText(s.get(0).getIngredients());
        urlx.setText(s.get(0).getUrl());
        new GetImageAsyncTask(Recipes.this).execute(imageUrl);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton imageButton2 = (ImageButton) findViewById(R.id.imageButton2);

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition=0;
                title.setText(result.get(0).getTitle());
                ing.setText(result.get(0).getIngredients());
                urlx.setText(result.get(0).getUrl());
                new GetImageAsyncTask(Recipes.this).execute(imageUrl);

            }
        });

        ImageButton imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition > 0) {
                    currentPosition--;
                    title.setText(result.get(currentPosition).getTitle());
                    ing.setText(result.get(currentPosition).getIngredients());
                    urlx.setText(result.get(currentPosition).getUrl());
                    urlx.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(result.get(currentPosition).getUrl()));
                            startActivity(intent);
                        }
                    });
                    new GetImageAsyncTask(Recipes.this).execute(imageUrl);
                } else {
                    Toast.makeText(Recipes.this, "This is the first recipe", Toast.LENGTH_LONG).show();
                }

            }
        });
        ImageButton imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition < result.size() - 1) {
                    currentPosition++;
                    title.setText(result.get(currentPosition).getTitle());
                    ing.setText(result.get(currentPosition).getIngredients());
                    urlx.setText(result.get(currentPosition).getUrl());
                    urlx.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(result.get(currentPosition).getUrl()));
                            startActivity(intent);
                        }
                    });
                    new GetImageAsyncTask(Recipes.this).execute(imageUrl);
                } else {
                    Toast.makeText(Recipes.this, "This is the last recipe", Toast.LENGTH_LONG).show();
                }
            }
        });
        ImageButton imageButton5 = (ImageButton) findViewById(R.id.imageButton5);

        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition = result.size()-1;
                title.setText(result.get(result.size() - 1).getTitle());
                ing.setText(result.get(result.size() - 1).getIngredients());
                urlx.setText(result.get(result.size() - 1).getUrl());
                urlx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(result.get(result.size() - 1).getUrl()));
                        startActivity(intent);
                    }
                });
                new GetImageAsyncTask(Recipes.this).execute(imageUrl);
            }
        });
    }

    @Override
    public void setupImage(Bitmap result) {
        ImageView img = (ImageView) findViewById(R.id.imageView3);
        img.setImageBitmap(result);
    }

}
