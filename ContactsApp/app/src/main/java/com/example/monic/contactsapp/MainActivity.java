package com.example.monic.contactsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_CODE = 1;
    static ArrayList<Contact> contactLists = new ArrayList<Contact>();

    static final String CONTACT_ADD = "contactadd";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("DEMOOO ", "I am here1");
        if(requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Contact contact = (Contact) data.getExtras().getSerializable(CONTACT_ADD);
                Log.d("DEMOOO ", contact.getFirstName());
                contactLists.add(contact);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactLists.add(new Contact("Shanmukhi","Nalamothu","UNCC","9874563210","snalamothu@uncc.edu","www.google.com","9312E Kittansett Dr","10/05/1994","Shannu","www.fb.com","www.twitter.com",null,"www.youtube.com",null));
        contactLists.add(new Contact("Vinutna","Gannu","UNCC","9874563210","vgannu@uncc.edu","www.google.com","9312E Kittansett Dr","10/05/1994","vinnu","www.fb.com","www.twitter.com",null,"www.youtube.com",null));


        Button create = (Button) findViewById(R.id.buttonCreate);
        Button edit = (Button) findViewById(R.id.buttonEdit);
        Button delete = (Button) findViewById(R.id.buttonDelete);
        Button display = (Button) findViewById(R.id.buttonDisplay);
        Button finish = (Button) findViewById(R.id.buttonFinish);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateContact.class);
                startActivityForResult(intent, REQUEST_CODE);
                //startActivity(intent);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactListView.class);
                intent.putExtra("contactNumbers", contactLists);
                intent.putExtra("mode", "edit");
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactListView.class);
                intent.putExtra("contactNumbers", contactLists);
                intent.putExtra("mode", "delete");
                startActivity(intent);

            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactListView.class);
                intent.putExtra("contactNumbers", contactLists);
                intent.putExtra("mode", "display");
                startActivity(intent);
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
