package com.example.monic.tripplanner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SignUp extends AppCompatActivity {
    String firstname,lastname,emails,password,repeatpass,birthday;
    int age;
    Users socialuser;
    ArrayList<Users> addNewFriends;
    FirebaseUser user;

    private FirebaseAuth mAuth;
    Button signupButton,cancelButton;
    EditText fname,lname,mail,bday,pwd,repeatpwd;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mRootRef;
    DatabaseReference mNoteRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mNoteRef = mRootRef.child("Users");

        fname = (EditText)findViewById(R.id.firstName);
        lname = (EditText)findViewById(R.id.lastName);
        mail = (EditText)findViewById(R.id.emailsignup);
//        bday = (EditText)findViewById(R.id.birthDay);
        pwd = (EditText)findViewById(R.id.password);
        repeatpwd = (EditText)findViewById(R.id.confirmPassword);


//        final Calendar myCalendar = Calendar.getInstance();
//
//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                String myFormat = "MM/dd/yyyy"; //In which you need put here
//                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                bday.setText(sdf.format(myCalendar.getTime()));
//            }
//
//        };
//        final DatePickerDialog dpdialog = new DatePickerDialog(SignUp.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
//        dpdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//        bday.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                dpdialog.show();
//            }
//        });


        signupButton = (Button)findViewById(R.id.signupButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUpNow();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });



    }

    private void signUpNow() {
        emails = mail.getText().toString();
        password = pwd.getText().toString();
        firstname = fname.getText().toString();
        lastname = lname.getText().toString();
        repeatpass = repeatpwd.getText().toString();

//        if (bday.getText() != null) {
//            birthday = bday.getText().toString();
//        }
//        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//        Date bDate = new Date();
//
//        try {
//            bDate = sdf.parse(birthday);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long ageInMillis = bDate.getTime() - (System.currentTimeMillis());
//
//        Date ages = new Date(ageInMillis);
//
//        age = ages.getYear();

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
//        if(TextUtils.isEmpty(birthday)){
//            Toast.makeText(this,"Incorrect Password",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(age <= 13)
//        {
//            Toast.makeText(this,"Age must be greater than 13 years",Toast.LENGTH_SHORT).show();
//            return;
//        }

        if(password.equals(repeatpass)) {
            mAuth.createUserWithEmailAndPassword(emails, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (task.isSuccessful()) {

                                UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                                                   .setDisplayName(fname + " " + lname).build();

                                user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updateProfile(changeRequest);

                                socialuser = new Users();
                                socialuser.setUserId(user.getUid());
                                socialuser.setFirstName(firstname);
                                socialuser.setLastName(lastname);
                                socialuser.setEmail(user.getEmail());
                                socialuser.setPassword(password);
                                saveUser(socialuser);
                            } else {
                                Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                // Toast.makeText(SignUpActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
            Toast.makeText(SignUp.this, "Password miss matched", Toast.LENGTH_SHORT).show();

    }

    private void saveUser(Users socialUser) {
        mNoteRef.child(socialUser.getUserId()).setValue(socialUser);
        Toast.makeText(SignUp.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(SignUp.this,MainActivity.class);
        startActivity(i);
    }
}
