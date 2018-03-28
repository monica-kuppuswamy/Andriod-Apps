package com.example.monic.contactsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

import static android.R.attr.data;
import static com.example.monic.contactsapp.MainActivity.contactLists;

public class ContactListView extends AppCompatActivity {
    static final int REQUEST_CODE = 1;
    static final String CONTACT_ADD = "contactadd";
    String displsyMode="";
    int index;
    int indexdelete;
    ScrollView sv_main;
    LinearLayout container;
    AlertDialog alert;

    ArrayList<Contact> listOfUsers = new ArrayList<Contact>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list_view);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you really want to delete this?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listOfUsers.remove(indexdelete);
                        contactLists=listOfUsers;
                        buildUI(listOfUsers);

                    }
                })
                .setNegativeButton("no" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alert = builder.create();

        sv_main = (ScrollView) findViewById(R.id.svmain);

        Intent i = getIntent();
        if (i != null) {
            listOfUsers = (ArrayList<Contact>) i.getSerializableExtra("contactNumbers");
            displsyMode = i.getStringExtra("mode");
            Log.d("demo","intent came");

            buildUI(listOfUsers);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Contact contact = (Contact) data.getExtras().getSerializable(CONTACT_ADD);
                listOfUsers.set(index,contact);
                contactLists = listOfUsers;
                buildUI(listOfUsers);

            }
        }
    }

    private void buildUI(ArrayList<Contact> contacts){

        container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        Log.d("demo",contacts.size()+"");

        for (final Contact user: contacts){
            final Contact c = user;
            final ContactList item = new ContactList(this);
            View itemView = (View)item;
            item.firstName.setText(user.firstName);
            item.lastName.setText(user.lastName);
            item.phoneNumber.setText(user.phoneNumber);

            if(user.imageURI != null){
                Log.d("demo", user.imageURI.toString());
                item.profileImage.setImageURI(user.getImageURI());
            }
            container.addView(itemView);
            final String fName = user.firstName;
            final String lName = user.lastName;
            final String finalDisplsyMode = displsyMode;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(finalDisplsyMode.equals("display")) {
                        Intent intent = new Intent(ContactListView.this, DetailedView.class);
                        intent.putExtra("contactDetails", c);
                        startActivity(intent);
                    }
                    else if(finalDisplsyMode.equals("edit")) {
                        Intent intent = new Intent(ContactListView.this, EditView.class);
                        intent.putExtra("contactDetails", c);
                        index = listOfUsers.indexOf(user);

                        startActivityForResult(intent,REQUEST_CODE);

                    }
                    else if(finalDisplsyMode.equals("delete")){
                        indexdelete = listOfUsers.indexOf(user);
                        alert.show();


                    }
                }
            });
        }
        sv_main.removeAllViews();
        sv_main.addView(container);
    }
}
