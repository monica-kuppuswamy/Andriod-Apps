package com.example.monic.creatorapp;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_CODE = 1;
    static final String IMAGE_KEY="imagename";
    int seekBarValue=0;
    String deptnames= "SIS";
    String emotion = "angry";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final ImageView avatarselect = (ImageView)findViewById(R.id.imageView1);
        if(requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Log.d("demo","ok");
                String tag = data.getExtras().getString(IMAGE_KEY);
                switch(tag)
                {
                    case "bg1" : avatarselect.setImageResource(R.drawable.avatar_f_1);
                                avatarselect.setTag("avatar_f_1");
                                break;
                    case "bg2" : avatarselect.setImageResource(R.drawable.avatar_f_2);
                        avatarselect.setTag("avatar_f_2");
                                break;
                    case "bg3"  : avatarselect.setImageResource(R.drawable.avatar_f_3);
                        avatarselect.setTag("avatar_f_3");
                                break;
                    case "bg4"  : avatarselect.setImageResource(R.drawable.avatar_m_3);
                        avatarselect.setTag("avatar_m_3");
                                break;
                    case "bg5"  : avatarselect.setImageResource(R.drawable.avatar_m_2);
                        avatarselect.setTag("avatar_m_2");
                                break;
                    case "bg6"  : avatarselect.setImageResource(R.drawable.avatar_m_1);
                        avatarselect.setTag("avatar_m_1");
                                break;
                }
            }
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText name = (EditText)findViewById(R.id.edit1);
        final EditText email = (EditText)findViewById(R.id.edit2);
        final ImageView avatarselect = (ImageView)findViewById(R.id.imageView1);
        avatarselect.setTag("profilePic");
        final ImageView emojiselect = (ImageView)findViewById(R.id.imageView2);
        emojiselect.setTag("angry");
        final RadioGroup dept = (RadioGroup)findViewById(R.id.radiogroup);
        final RadioButton sisdept = (RadioButton)findViewById(R.id.radio1);
        final RadioButton csdept = (RadioButton)findViewById(R.id.radio2);
        final RadioButton biodept = (RadioButton)findViewById(R.id.radio3);
        final TextView mooddisplay = (TextView)findViewById(R.id.textView2);
        final Button submitbtn = (Button)findViewById(R.id.button1);
        final SeekBar seekbar = (SeekBar) findViewById(R.id.seekBar2);

        avatarselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SelectAvatar.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        dept.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                RadioButton Rb1 = (RadioButton) findViewById(checkedId);
                if(Rb1.getText().equals("SIS")) {
                    deptnames = "SIS";
                    Log.d("demo","1");
                }
                else if(Rb1.getText().equals("CS")) {
                    deptnames = "CS";
                }
                else if(Rb1.getText().equals("BIO")) {
                    deptnames = "BIO";
                }


            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                               @Override
                                               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                   seekBarValue = seekBar.getProgress();
                                                   switch (seekBarValue) {
                                                       case 0:
                                                           emojiselect.setImageResource(R.drawable.angry);
                                                           emojiselect.setTag("angry");
                                                           mooddisplay.setText("Your current mood: Angry");
                                                           break;
                                                       case 1:
                                                           emojiselect.setImageResource(R.drawable.sad);
                                                           emojiselect.setTag("sad");
                                                           mooddisplay.setText("Your current mood: Sad");
                                                           break;
                                                       case 2:
                                                           emojiselect.setImageResource(R.drawable.happy);
                                                           emojiselect.setTag("happy");
                                                           mooddisplay.setText("Your current mood: Happy");
                                                           break;
                                                       case 3:
                                                           emojiselect.setImageResource(R.drawable.awesome);
                                                           emojiselect.setTag("awesome");
                                                           mooddisplay.setText("Your current mood: Awesome");
                                                           break;
                                                       default:
                                                           emojiselect.setImageResource(R.drawable.angry);
                                                           emojiselect.setTag("angry");
                                                           mooddisplay.setText("Your current mood: Angry");
                                                   }
                                               }

                                               @Override
                                               public void onStartTrackingTouch(SeekBar seekBar) {

                                               }

                                               @Override
                                               public void onStopTrackingTouch(SeekBar seekBar) {

                                               }
                                           });
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                String imageTag = String.valueOf(avatarselect.getTag());
                String emailID = email.getText().toString();
                String profileName = name.getText().toString();
                emotion = String.valueOf(emojiselect.getTag());
                String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                if (!emailID.matches(emailPattern)) {
                    Toast.makeText(MainActivity.this, "Enter valid email address", Toast.LENGTH_LONG).show();
                }
                else if (imageTag == "profilePic") {
                    Toast.makeText(MainActivity.this, "Select profile pic", Toast.LENGTH_LONG).show();

                }
                else if (profileName == null || profileName.equals("")) {
                    Toast.makeText(MainActivity.this, "Enter Profile name", Toast.LENGTH_LONG).show();
                } else {
                    Profile p = new Profile(imageTag, profileName, emailID, deptnames, emotion);
                    intent.putExtra("profile", p);
                    startActivity(intent);
                }
            }
        });


    }
}
