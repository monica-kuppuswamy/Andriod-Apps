package com.example.monic.mysocialapp;

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

import java.util.ArrayList;
import java.util.List;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by monic on 11/21/2017.
 */

public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.ViewHolder>{
    List<Posts> mData = new ArrayList<Posts>();

    private static int counter = 0;
    static UserWall mainActivity;

    FirebaseUser user;
    private FirebaseAuth mAuth;

    public UserPostAdapter(UserWall mainActivity, List<Posts> mData) {
        this.mData = mData;
        this.mainActivity = mainActivity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView postOwner;
        TextView postedTime;
        TextView postedContent;
        ImageView buttonRemove;

        public ViewHolder(View v) {
            super(v);
            postOwner = v.findViewById(R.id.newfriendAddName1);
            postedTime = v.findViewById(R.id.postTime);
            postedContent = v.findViewById(R.id.postMessage);
            buttonRemove = v.findViewById(R.id.deleteButton);
        }
    }

    @Override
    public UserPostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_posts, parent, false);

        UserPostAdapter.ViewHolder viewHolder = new UserPostAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final UserPostAdapter.ViewHolder holder, final int position) {
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
        if(!p.getUserId().equals(user.getUid())) {
            holder.buttonRemove.setVisibility(View.INVISIBLE);
        }
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

        holder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                final DatabaseReference mNoteRef = mRootRef.child("Users");
                mAuth = FirebaseAuth.getInstance();
                user = mAuth.getCurrentUser();
                DatabaseReference mpendingRef = mNoteRef.child(user.getUid()).child("Posts");
                mpendingRef.child(p.getId()).removeValue();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
}

