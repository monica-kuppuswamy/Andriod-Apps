package com.example.monic.mysocialapp;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.List;

/**
 * Created by monic on 11/21/2017.
 */

public class HomePostAdapter extends RecyclerView.Adapter<HomePostAdapter.ViewHolder>{
    List<Posts> mData = new ArrayList<Posts>();

    private static int counter = 0;
    static Home mainActivity;

    DatabaseReference mNoteRef;
    DatabaseReference mFriendRef1;
    DatabaseReference mFriendRef2;
    FirebaseUser user;
    private FirebaseAuth mAuth;

    public HomePostAdapter(Home mainActivity, List<Posts> mData) {
        this.mData = mData;
        this.mainActivity = mainActivity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView postOwner;
        TextView postedTime;
        TextView postedContent;

        public ViewHolder(View v) {
            super(v);
            postOwner = v.findViewById(R.id.user_post_owner);
            postedTime = v.findViewById(R.id.posted_time);
            postedContent = v.findViewById(R.id.posted_message);
        }
    }

    @Override
    public HomePostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_post_item, parent, false);

        HomePostAdapter.ViewHolder viewHolder = new HomePostAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HomePostAdapter.ViewHolder holder, final int position) {
        final Posts p = mData.get(position);

        DateFormat convertFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        PrettyTime prettyTime = new PrettyTime();
        String prettyTimeString = null;
        try {
            prettyTimeString = prettyTime.format(convertFormat.parse(p.getPostTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.postedTime.setText(prettyTimeString);
        holder.postedContent.setText(p.getPostContent());
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mNoteRef = mRootRef.child("Users");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mNoteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Users users = postSnapshot.getValue(Users.class);
                    if(users.getUserId().equals(p.getUserId())) {
                        holder.postOwner.setText(users.getName(p.getUserId()));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.postOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mainActivity, UserWall.class);
                i.putExtra("appUserName", holder.postOwner.getText().toString());
                mainActivity.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
}

