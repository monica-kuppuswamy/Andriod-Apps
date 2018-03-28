package com.example.monic.tripplanner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText usernametext;
    EditText passwordtext;
    Button loginButton;
    TextView signupButton;
    String username,password;
    GoogleApiClient mgoogleapiclient;
    ArrayList<String> signedUpUsers;
    DatabaseReference databaseReference;
    Users socialuser1;
    DatabaseReference mRootRef;
    DatabaseReference mNoteRef;
    GoogleSignInAccount acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mNoteRef = mRootRef.child("Users");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Demo", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("Demo", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                signedUpUsers = new ArrayList<String>();
                if(dataSnapshot.getChildrenCount() > 0){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        signedUpUsers.add(snapshot.child("email").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            //Log.d("Demo",user.getUid());
            Intent intent = new Intent(MainActivity.this,Home.class);
            //intent.putExtra("uid",user.getUid());
            startActivity(intent);
            // User is signed in
        }
        usernametext = (EditText)findViewById(R.id.tv_username);
        passwordtext = (EditText)findViewById(R.id.tv_password);

        loginButton = (Button)findViewById(R.id.buttonLogin);
        signupButton = (TextView) findViewById(R.id.buttonSignUp);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginNow();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.googleSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("465062120244-h8v8vh9l5ue461pr4ha3hgb1iudqrslf.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mgoogleapiclient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

    }

    private void loginNow() {
        username = usernametext.getText().toString();
        password = passwordtext.getText().toString();
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Incorrect Email Id",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Incorrect Password",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {
                            if(password.length()<6){
                                Toast.makeText(MainActivity.this,"Password should have minimum 6 characters",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,Home.class);
                            startActivity(intent);
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mgoogleapiclient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Demo","handle sign in result" + result.isSuccess());
        if(result.isSuccess()) {
            acc = result.getSignInAccount();
            AuthCredential credential = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("demo", "signInWithCredential:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w("demo", "signInWithCredential", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                //signed in;
                                Log.d("Demo","sign in using google successful");
                                if (!signedUpUsers.contains(acc.getEmail())) {
                                    socialuser1 = new Users();
                                    String personGivenName = acc.getGivenName();
                                    String personFamilyName = acc.getFamilyName();
                                    String personEmail = acc.getEmail();
                                    String personId = mAuth.getCurrentUser().getUid();

                                    socialuser1.setUserId(personId);
                                    socialuser1.setFirstName(acc.getGivenName());
                                    socialuser1.setLastName(acc.getFamilyName());
                                    socialuser1.setEmail(acc.getEmail());
                                    saveuser(socialuser1);
                                    //databaseReference.updateChildren(childUpdates);
                                    finish();
                                }

                                Intent intent = new Intent(MainActivity.this,Home.class);
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                startActivity(intent);
                            }

                        }
                    });
        }
        else
        {

        }
    }


    private void saveuser(Users socialUser1) {
        mNoteRef.child(socialUser1.getUserId()).setValue(socialUser1);
        Toast.makeText(MainActivity.this, "Successfully Registered using Google", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(MainActivity.this,Home.class);
        startActivity(i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Demo","On Connection Failed" + connectionResult);
    }
}
