package com.example.monic.mysocialapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

/**
 * Created by monic on 11/20/2017.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder>{
    List<String> mData = new ArrayList<String>();

    private static int counter = 0;
    static ManageFriends mainActivity;

    DatabaseReference mNoteRef;
    DatabaseReference mFriendRef1;
    DatabaseReference mFriendRef2;
    FirebaseUser user;
    private FirebaseAuth mAuth;

    public FriendAdapter(ManageFriends mainActivity, List<String> mData) {
        this.mData = mData;
        this.mainActivity = mainActivity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView friend;
        ImageView buttonRemove;

        public ViewHolder(View v) {
            super(v);
            friend = v.findViewById(R.id.friendName);
            buttonRemove = v.findViewById(R.id.removeFriendButton);
        }
    }

    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friends_item, parent, false);

        FriendAdapter.ViewHolder viewHolder = new FriendAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FriendAdapter.ViewHolder holder, final int position) {
        final String friend = mData.get(position);
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mNoteRef = mRootRef.child("Users");
        mNoteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Users users = postSnapshot.getValue(Users.class);
                    if(users.getUserId().equals(friend)) {
                        holder.friend.setText(users.getName(friend));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mainActivity, UserWall.class);
                i.putExtra("appUserName", holder.friend.getText().toString());
                mainActivity.startActivity(i);
            }
        });

        holder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                user = mAuth.getCurrentUser();
                DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                final DatabaseReference mNoteRef = mRootRef.child("Users");
                mNoteRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mFriendRef1 = mNoteRef.child(user.getUid()).child("Friends");
                        mFriendRef2 = mNoteRef.child(friend).child("Friends");

                        mFriendRef1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                    String f = postSnapshot.getValue(String.class);
                                    if(f.equals(friend)) {
                                        mFriendRef1.child(postSnapshot.getKey()).removeValue();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        mFriendRef2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                    String f = postSnapshot.getValue(String.class);
                                    if(f.equals(user.getUid())) {
                                        mFriendRef2.child(postSnapshot.getKey()).removeValue();
                                    }
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
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
}
