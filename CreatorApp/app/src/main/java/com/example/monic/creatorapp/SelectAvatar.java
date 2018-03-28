package com.example.monic.creatorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class SelectAvatar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);

        final ImageView img4 = (ImageView) findViewById(R.id.imageView1);
        final ImageView img5 = (ImageView) findViewById(R.id.imageView2);
        final ImageView img6 = (ImageView) findViewById(R.id.imageView3);
        final ImageView img1 = (ImageView) findViewById(R.id.imageView4);
        final ImageView img2 = (ImageView) findViewById(R.id.imageView5);
        final ImageView img3 = (ImageView) findViewById(R.id.imageView6);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String backgroundImageName = String.valueOf(img1.getTag());
                Log.d("demo",backgroundImageName);
                Intent intent = new Intent();
                intent.putExtra(MainActivity.IMAGE_KEY,backgroundImageName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String backgroundImageName = String.valueOf(img2.getTag());
                Log.d("demo",backgroundImageName);
                Intent intent = new Intent();
                intent.putExtra(MainActivity.IMAGE_KEY,backgroundImageName);
                setResult(RESULT_OK, intent);
                finish();

            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String backgroundImageName = String.valueOf(img3.getTag());
                Log.d("demo",backgroundImageName);
                Intent intent = new Intent();
                intent.putExtra(MainActivity.IMAGE_KEY,backgroundImageName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String backgroundImageName = String.valueOf(img4.getTag());
                Log.d("demo",backgroundImageName);
                Intent intent = new Intent();
                intent.putExtra(MainActivity.IMAGE_KEY,backgroundImageName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String backgroundImageName = String.valueOf(img5.getTag());
                Log.d("demo",backgroundImageName);
                Intent intent = new Intent();
                intent.putExtra(MainActivity.IMAGE_KEY,backgroundImageName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String backgroundImageName = String.valueOf(img6.getTag());
                Log.d("demo",backgroundImageName);
                Intent intent = new Intent();
                intent.putExtra(MainActivity.IMAGE_KEY,backgroundImageName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}