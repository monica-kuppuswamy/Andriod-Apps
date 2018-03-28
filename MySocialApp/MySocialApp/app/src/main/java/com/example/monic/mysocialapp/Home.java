package com.example.monic.mysocialapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Home extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mNoteRef = mRootRef.child("Users");
    DatabaseReference mPostRef;

    String postContent;
    String id;
    FirebaseUser user;
    String f;

    TextView userDisplayName;
    EditText userPost;
    ImageView buttonPost, buttonFriends;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    List<Posts> userPosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.app_icon);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userDisplayName = (TextView) findViewById(R.id.homeUser);
        userPost = (EditText) findViewById(R.id.writePost);
        buttonPost = (ImageView) findViewById(R.id.postButton);
        buttonFriends = (ImageView) findViewById(R.id.friendsButton);
        userDisplayName.setText(user.getDisplayName());

        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postContent = userPost.getText().toString();
                Posts newPost = new Posts();
                newPost.setUserId(user.getUid());
                newPost.setPostContent(postContent);
                Date outputDate = new Date();
                SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                String postedDateTime = newDateFormat.format(outputDate);
                newPost.setPostTime(postedDateTime);
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

        userDisplayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, UserWall.class);
                i.putExtra("appUserName", userDisplayName.getText().toString());
                startActivity(i);
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

    @Override
    protected void onStart() {
        super.onStart();

        mNoteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Users users1 = postSnapshot.getValue(Users.class);
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

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        DatabaseReference mcurrentUserRef = mNoteRef.child(user.getUid()).child("Posts");
        mcurrentUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userPosts.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Posts f = postSnapshot.getValue(Posts.class);
                    userPosts.add(f);
                }

                mRecyclerView = (RecyclerView) findViewById(R.id.homePosts);
                mLayoutManager = new LinearLayoutManager(Home.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                Collections.sort(userPosts, new Home.CustomComparator());
                Collections.reverse(userPosts);
                mAdapter = new HomePostAdapter(Home.this, userPosts);
                mRecyclerView.setAdapter(mAdapter);

                DatabaseReference mpendingRef = mNoteRef.child(user.getUid()).child("Friends");
                mpendingRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            f = postSnapshot.getValue(String.class);
                            Log.d("demo", "friend post " + f);
                            DatabaseReference mpostRef = mNoteRef.child(f).child("Posts");
                            mpostRef.addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                        Posts pp = postSnapshot.getValue(Posts.class);
                                        userPosts.add(pp);
                                    }
                                    Log.d("demo", "number of home posts" + userPosts.size());
                                    mRecyclerView = (RecyclerView) findViewById(R.id.homePosts);
                                    mLayoutManager = new LinearLayoutManager(Home.this);
                                    mRecyclerView.setLayoutManager(mLayoutManager);
                                    Collections.sort(userPosts, new Home.CustomComparator());
                                    Collections.reverse(userPosts);
                                    mAdapter = new HomePostAdapter(Home.this, userPosts);
                                    mRecyclerView.setAdapter(mAdapter);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }

                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void savePost(Posts userPost) {
        mPostRef.child(id).setValue(userPost);
    }


    private class CustomComparator implements Comparator<Posts> {
        Date d1, d2;
        @Override
        public int compare(Posts posts, Posts t1) {
            DateFormat convertFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            try {
                d1 = convertFormat.parse(posts.getPostTime());
                d2 = convertFormat.parse(t1.getPostTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return d1.compareTo(d2);
        }
    }
}
