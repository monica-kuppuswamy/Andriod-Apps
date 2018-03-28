package com.example.monic.creatorapp;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        if (getIntent().getExtras() != null) {
            Profile p = (Profile) getIntent().getExtras().getSerializable("profile");
            TextView tv1 = (TextView) findViewById(R.id.textView1);
            TextView tv2 = (TextView) findViewById(R.id.textView2);
            TextView tv3 = (TextView) findViewById(R.id.textView3);
            TextView tv4 = (TextView) findViewById(R.id.textView4);
            ImageView i1 = (ImageView) findViewById(R.id.imageView1);
            ImageView i2 = (ImageView) findViewById(R.id.imageView2);

            tv1.setText(p.name);
            tv2.setText(p.email);
            tv3.setText(p.department);
            tv4.setText(p.emotion);

            switch (p.imageTag) {
                case "avatar_f_1":
                    i1.setImageResource(R.drawable.avatar_f_1);
                    break;
                case "avatar_f_2":
                    i1.setImageResource(R.drawable.avatar_f_2);
                    break;
                case "avatar_f_3":
                    i1.setImageResource(R.drawable.avatar_f_3);
                    break;
                case "avatar_m_1":
                    i1.setImageResource(R.drawable.avatar_m_1);
                    break;
                case "avatar_m_2":
                    i1.setImageResource(R.drawable.avatar_m_2);
                    break;
                case "avatar_m_3":
                    i1.setImageResource(R.drawable.avatar_m_3);
                    break;
            }

            switch (p.emotion) {
                case "sad":
                    i2.setImageResource(R.drawable.sad);
                    break;
                case "happy":
                    i2.setImageResource(R.drawable.happy);
                    break;
                case "angry":
                    i2.setImageResource(R.drawable.angry);
                    break;
                case "awesome":
                    i2.setImageResource(R.drawable.awesome);
                    break;
            }
        }
    }
}
