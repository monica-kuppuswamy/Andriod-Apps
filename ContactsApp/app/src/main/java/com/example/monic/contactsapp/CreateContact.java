package com.example.monic.contactsapp;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateContact extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    Bitmap photo;
    Uri imageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);


        final EditText fname = (EditText) findViewById(R.id.firstName);
        final EditText lname = (EditText) findViewById(R.id.lastName);
        final EditText comname = (EditText) findViewById(R.id.companyName);
        final EditText phonenum = (EditText) findViewById(R.id.phone);
        final EditText email = (EditText) findViewById(R.id.emailID);
        final EditText url = (EditText) findViewById(R.id.urlName);
        final EditText address = (EditText) findViewById(R.id.address);
        final EditText birthday = (EditText) findViewById(R.id.birthDay);
        final EditText nickname = (EditText) findViewById(R.id.nickName);
        final EditText fburl = (EditText) findViewById(R.id.facebookURL);
        final EditText twitter = (EditText) findViewById(R.id.TwitterURL);
        final EditText skype = (EditText) findViewById(R.id.skypeID);
        final EditText youtube = (EditText) findViewById(R.id.youTube);
        imageView = (ImageView) findViewById(R.id.imageView2);
        Button savebutton = (Button) findViewById(R.id.buttonSave);

        findViewById(R.id.imageView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(CreateContact.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(CreateContact.this,
                            new String[]{Manifest.permission.CAMERA},
                            1001);
                }
                else {

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    if (cameraIntent.resolveActivity(getPackageManager()) != null)
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                birthday.setText(sdf.format(myCalendar.getTime()));
            }

        };
        final DatePickerDialog dpdialog = new DatePickerDialog(CreateContact.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        dpdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dpdialog.show();
               /* new DatePickerDialog(CreateContact.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = "";
                String lastName = "";
                String companyName = "";
                String phoneNumber = "";
                String emailId = "";
                String urlName = "";
                String localAddress = "";
                String birthDate = "";
                String nickName = "";
                String fbUrl = "";
                String twitterUrl = "";
                String skypeId = "";
                String youTube = "";

                if (fname.getText() != null) {
                    firstName = fname.getText().toString();
                }
                if (lname.getText() != null) {
                    lastName = lname.getText().toString();
                }
                if (comname.getText() != null) {
                    companyName = comname.getText().toString();
                }
                if (phonenum.getText() != null) {
                    phoneNumber = phonenum.getText().toString();
                }
                if (email.getText() != null) {
                    emailId = email.getText().toString();
                }
                if (url.getText() != null) {
                    urlName = url.getText().toString();
                }
                if (address.getText() != null) {
                    localAddress = address.getText().toString();
                }
                if (birthday.getText() != null) {
                    birthDate = birthday.getText().toString();
                }
                if (nickname.getText() != null) {
                    nickName = nickname.getText().toString();
                }
                if (fburl.getText() != null) {
                    fbUrl = fburl.getText().toString();
                }
                if (twitter.getText() != null) {
                    twitterUrl = twitter.getText().toString();
                }
                if (skype.getText() != null) {
                    skypeId = skype.getText().toString();
                }
                if (youtube.getText() != null) {
                    youTube = youtube.getText().toString();
                }


                String validatePhoneNum = "^\\+?1?\\d{10}$";
                String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date dateToCompare = new Date();
                Date bDate = new Date();
                try {
                    dateToCompare = sdf.parse("01/01/1850");
                    bDate = sdf.parse(birthDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Pattern pattern = Pattern.compile(validatePhoneNum);
                Matcher matcher = pattern.matcher(phoneNumber);

                // Validations for mandatory fields , email, phone number and birth date
                if (bDate != null && bDate.before(dateToCompare)) {
                    Toast.makeText(CreateContact.this, "Birthdate should not be before Jan 1, 1850", Toast.LENGTH_LONG).show();
                } else if (firstName.equals("") || lastName.equals("") || phoneNumber.equals("")) {
                    Toast.makeText(CreateContact.this, "First, Last Name & Phone Number are mandatory fields. Please Enter.", Toast.LENGTH_LONG).show();
                } else if (!(matcher.matches())) {
                    Toast.makeText(CreateContact.this, "Invalid phone number.", Toast.LENGTH_LONG).show();
                } else if (!(emailId.equals("")) && !(emailId.matches(emailPattern))) {
                    Toast.makeText(CreateContact.this, "Invalid Email ID.", Toast.LENGTH_LONG).show();
                } else {

                    // Create a new contact if all the fields are valid
                    Contact contact = new Contact(firstName, lastName, companyName, phoneNumber, emailId, urlName
                            , localAddress, birthDate, nickName, fbUrl, twitterUrl, skypeId, youTube, imageURI);
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.CONTACT_ADD, contact);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d("demo","called");

        // Adding Image shot on Camera
        if (requestCode == CAMERA_REQUEST && resultCode == AppCompatActivity.RESULT_OK) {
            Log.d("demo","passed");
            photo = (Bitmap) intent.getExtras().get("data");
            imageView.setImageBitmap(photo);
            imageURI = getImageUri(getApplicationContext(), photo);
            imageView.setImageURI(imageURI);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1001: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    if (cameraIntent.resolveActivity(getPackageManager()) != null)
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);

                } else {

                    Toast.makeText(this, "Please grant camera permissions", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 1002: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the

                    imageURI = getImageUri(getApplicationContext(), photo);
                    imageView.setImageURI(imageURI);


                } else {

                    Toast.makeText(this, "Please grant camera permissions", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(CreateContact.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(CreateContact.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1002);
        }
        else {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
            return Uri.parse(path);
        }
        return null;
    }

}
