package com.example.monic.mysocialapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Home extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mNoteRef = mRootRef.child("Users");
    DatabaseReference mPostRef;

    String postContent;
    String id;
    FirebaseUser user;

    TextView userDisplayName;
    EditText userPost;
    ImageView buttonPost, buttonFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userDisplayName = (TextView) findViewById(R.id.homeUser);
        userPost = (EditText) findViewById(R.id.writePost);
        buttonPost = (ImageView) findViewById(R.id.postButton);
        buttonFriends = (ImageView) findViewById(R.id.friendsButton);

        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postContent = userPost.getText().toString();
                Posts newPost = new Posts();
                newPost.setUserId(user.getUid());
                newPost.setPostContent(postContent);
                Date outputDate = new Date();
                Date newone = null;
                SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                try {
                    newone = newDateFormat.parse(newDateFormat.format(outputDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                PrettyTime prettyTime=new PrettyTime();
                String prettyTimeString=prettyTime.format(newone);
                newPost.setPostTime(prettyTimeString);
                mPostRef = mNoteRef.child(user.getUid()).child("Posts");
                id = mPostRef.push().getKey();
                newPost.setId(id);
                savePost(newPost);
                userPost.setText("");
            }
        });

        buttonFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, ManageFriends.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mNoteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Users users1 = postSnapshot.getValue(Users.class);
                    Log.d("Demo", users1.getUserId());
                    Log.d("Demo", user.getUid());
                    if(users1.getUserId().equals(user.getUid())) {
                        userDisplayName.setText(users1.getFirstName() + " " + users1.getLastName());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void savePost(Posts userPost) {
        mPostRef.child(id).setValue(userPost);
    }
}
