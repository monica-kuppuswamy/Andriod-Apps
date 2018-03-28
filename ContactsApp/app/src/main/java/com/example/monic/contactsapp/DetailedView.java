package com.example.monic.contactsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailedView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        final Contact c = (Contact) getIntent().getExtras().getSerializable("contactDetails");

        ImageView profile = (ImageView)findViewById(R.id.imageView4);
        if(c.getImageURI() != null)
            profile.setImageURI(c.getImageURI());
        TextView firstName = (TextView)findViewById(R.id.tv1);
        firstName.setText(c.firstName);

        TextView lastName = (TextView)findViewById(R.id.tv2);
        lastName.setText(c.lastName);

        TextView number = (TextView)findViewById(R.id.tv3);
        number.setText(c.phoneNumber);

        TextView company = (TextView)findViewById(R.id.companyDetail);
        company.setText(c.company);

        TextView email = (TextView)findViewById(R.id.emailDetail);
        email.setText(c.emailID);

        if(c.getUrlName()!=null) {
            TextView url = (TextView)findViewById(R.id.urlDetail);
            url.setText(c.getUrlName());

            url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(c.getUrlName()));
                    startActivity(intent);
                }
            });
        }


        TextView address = (TextView)findViewById(R.id.addressDetail);
        address.setText(c.localAddress);

        TextView birthday = (TextView)findViewById(R.id.bdayDetail);
        birthday.setText(c.birthDate);

        TextView nickName = (TextView)findViewById(R.id.nicknameDetail);
        nickName.setText(c.nickName);

        if(c.getFbUrl()!=null) {
            TextView fbUrl = (TextView) findViewById(R.id.fbDetail);
            fbUrl.setText(c.getFbUrl());
            fbUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(c.getFbUrl()));
                    startActivity(intent);
                }
            });
        }

        if(c.getTwitterUrl()!=null) {
            TextView twitterUrl = (TextView) findViewById(R.id.twitterDetail);
            twitterUrl.setText(c.getTwitterUrl());
            twitterUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(c.getTwitterUrl()));
                    startActivity(intent);

                }
            });
        }

        if(c.getSkypeId()!=null) {
            TextView skypeid = (TextView) findViewById(R.id.skypeDetail);
            skypeid.setText(c.getSkypeId());
            skypeid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(c.getSkypeId()));
                    startActivity(intent);

                }
            });
        }

        if(c.getYouTube()!=null) {
            TextView youtube = (TextView) findViewById(R.id.youtubeDetail);
            youtube.setText(c.getYouTube());
            youtube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(c.getYouTube()));
                    startActivity(intent);
                }
            });
        }

    }
}