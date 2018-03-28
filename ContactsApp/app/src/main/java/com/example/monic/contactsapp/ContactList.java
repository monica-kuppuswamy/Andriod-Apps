package com.example.monic.contactsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ContactList extends LinearLayout {

    public TextView firstName, lastName, phoneNumber;
    ImageView profileImage;

    public ContactList (Context context) {
        super(context);
        inflateXML(context);
    }

    public void inflateXML(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.activity_contact_list, this);
        this.firstName = (TextView) findViewById(R.id.tv1);
        this.lastName = (TextView) findViewById((R.id.tv2));
        this.phoneNumber = (TextView) findViewById(R.id.tv3);
        this.profileImage = (ImageView) findViewById(R.id.imageView4);
    }

}