package com.example.monic.mysocialapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class EditProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button saveButton,cancelButton;
    EditText fname,lname,mail,bday,pwd,repeatpwd;
    String firstname,lastname,emails,password,repeatpass,birthday;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mRootRef;
    DatabaseReference mNoteRef;
    FirebaseUser user;
    Users socialuser;
    String name;
    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.app_icon);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mNoteRef = mRootRef.child("Users");

        fname = (EditText)findViewById(R.id.firstName);
        lname = (EditText)findViewById(R.id.lastName);
        mail = (EditText)findViewById(R.id.emailsignup);
        bday = (EditText)findViewById(R.id.birthDay);
        pwd = (EditText)findViewById(R.id.password);
        repeatpwd = (EditText)findViewById(R.id.confirmPassword);

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
                bday.setText(sdf.format(myCalendar.getTime()));
            }

        };
        final DatePickerDialog dpdialog = new DatePickerDialog(EditProfile.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        dpdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        bday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dpdialog.show();
            }
        });

        mNoteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Users users1 = postSnapshot.getValue(Users.class);
                    if(users1.getUserId().equals(user.getUid())) {
                        fname.setText(users1.getFirstName());
                        lname.setText(users1.getLastName());
                        mail.setText(users1.getEmail());
                        repeatpwd.setText(users1.getPassword());
                        mail.setFocusable(false);
                        pwd.setText(users1.getPassword());
                        bday.setText(users1.getBirthday());
                        name=users1.getName(user.getUid());

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        saveButton = (Button)findViewById(R.id.save);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveNow();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditProfile.this,UserWall.class);
                i.putExtra("appUserName",name);
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
// User chose the "Settings" item, show the app settings UI...
                mAuth.signOut();
                Intent loout = new Intent(getApplicationContext(), MainActivity.class);
                loout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loout);

            default:
// If we got here, the user's action was not recognized.
// Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void saveNow() {

        password = pwd.getText().toString();
        firstname = fname.getText().toString();
        lastname = lname.getText().toString();
        repeatpass = repeatpwd.getText().toString();
        emails = mail.getText().toString();


        if (bday.getText() != null) {
            birthday = bday.getText().toString();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date bDate = new Date();

        try {
            bDate = sdf.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ageInMillis = bDate.getTime() - (System.currentTimeMillis());

        Date ages = new Date(ageInMillis);

        age = ages.getYear();

        if(TextUtils.isEmpty(emails)){
            Toast.makeText(this,"Incorrect Email Id",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Incorrect Password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(firstname)){
            Toast.makeText(this,"Enter FirstName",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(lastname)){
            Toast.makeText(this,"Enter lastName",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(repeatpass)){
            Toast.makeText(this,"Incorrect Password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(birthday)){
            Toast.makeText(this,"Incorrect Password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(age <= 13)
        {
            Toast.makeText(this,"Age must be greater than 13 years",Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.equals(repeatpass)) {
            socialuser = new Users();
            socialuser.setFirstName(firstname);
            socialuser.setLastName(lastname);
            socialuser.setPassword(password);
            socialuser.setBirthday(birthday);
            socialuser.setEmail(emails);
            socialuser.setUserId(user.getUid());
            saveUser(socialuser);
        }
        else
            Toast.makeText(EditProfile.this, "Password miss matched", Toast.LENGTH_SHORT).show();

    }

    private void saveUser(Users socialUser) {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(socialUser.getFirstName() + " " + socialUser.getLastName()).build();
        user.updateProfile(changeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("demo-now", "saveUser: "+user.getDisplayName());
                finish();
            }
        });

        mNoteRef.child(socialUser.getUserId()).child("birthday").setValue(socialUser.getBirthday());
        mNoteRef.child(socialUser.getUserId()).child("firstName").setValue(socialUser.getFirstName());
        mNoteRef.child(socialUser.getUserId()).child("lastName").setValue(socialUser.getLastName());
        mNoteRef.child(socialUser.getUserId()).child("password").setValue(socialUser.getPassword());
        Toast.makeText(EditProfile.this, "Successfully Updated", Toast.LENGTH_SHORT).show();

    }
}
