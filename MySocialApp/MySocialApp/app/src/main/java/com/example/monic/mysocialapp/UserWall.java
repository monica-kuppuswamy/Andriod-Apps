package com.example.monic.mysocialapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class UserWall extends AppCompatActivity {

    TextView appUser;
    TextView postLabel;
    ImageView friendsButton;
    ImageView homeButton;
    ImageView peditButton;
    String[] name;
    String UId;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    List<Posts> userPosts = new ArrayList<>();
    FirebaseUser user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wall);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.app_icon);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (getIntent().getExtras() != null) {
            name = getIntent().getExtras().getString("appUserName").split(" ");
            Log.d("demo","name is" + name[0] + " " + name[1]);
        }


        postLabel = (TextView) findViewById(R.id.posts_label);
        appUser = (TextView) findViewById(R.id.app_user);
        friendsButton = (ImageView) findViewById(R.id.friends_button);
        homeButton = (ImageView) findViewById(R.id.home_button);
        peditButton = (ImageView) findViewById(R.id.profileEditButton);

        appUser.setText(name[0] + " " + name[1]);

        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserWall.this, ManageFriends.class);
                startActivity(i);
            }
        });
        peditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserWall.this,EditProfile.class);
                startActivity(i);


            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserWall.this, Home.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("demo-now","onresume");
       appUser.setText(name[0] + " " + name[1]);
        //appUser.setText(user.getDisplayName());
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
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference mNoteRef = mRootRef.child("Users");
        mNoteRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Users users1 = postSnapshot.getValue(Users.class);
                    Log.d("demo", "hey" + users1.getFirstName());
                    Log.d("demo", "hey" + users1.getLastName());
                    Log.d("demo", "hey" + name[0]);
                    Log.d("demo", "hey" + name[1]);

                    if(users1.getFirstName().equals(name[0]) && users1.getLastName().equals(name[1])) {
                        UId = users1.getUserId();
                        break;
                    }
                }
                if(UId.equals(user.getUid())) {
                    postLabel.setText("My Posts");
                    homeButton.setVisibility(View.INVISIBLE);
                } else {
                    postLabel.setText(name[0] + " " + name[1] + "'s Post");
                    friendsButton.setVisibility(View.INVISIBLE);
                    peditButton.setVisibility(View.INVISIBLE);
                }

                DatabaseReference mpendingRef = mNoteRef.child(UId).child("Posts");
                mpendingRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        appUser.setText(user.getDisplayName());
                        userPosts.clear();
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Posts f = postSnapshot.getValue(Posts.class);
                            userPosts.add(f);
                        }
                        mRecyclerView = (RecyclerView) findViewById(R.id.user_posts);
                        mLayoutManager = new LinearLayoutManager(UserWall.this);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        Collections.sort(userPosts, new UserWall.CustomComparator());
                        Collections.reverse(userPosts);
                        mAdapter = new UserPostAdapter(UserWall.this, userPosts);
                        mRecyclerView.setAdapter(mAdapter);
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
