package com.example.monic.mysocialapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monic on 11/19/2017.
 */

public class NewFriendsAdapter extends RecyclerView.Adapter<NewFriendsAdapter.ViewHolder> {

    List<Friends> mData = new ArrayList<Friends>();

    private static int counter = 0;
    static ManageFriends mainActivity;

    public NewFriendsAdapter(ManageFriends mainActivity, List<Friends> mData) {
        this.mData = mData;
        this.mainActivity = mainActivity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView friend;
        ImageView buttonAdd;

        public ViewHolder(View v) {
            super(v);
            friend = v.findViewById(R.id.newfriendAddName);
            buttonAdd = v.findViewById(R.id.addFriendButton);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_friend_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Friends friend = mData.get(position);
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mNoteRef = mRootRef.child("Users");
        mNoteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Users users = postSnapshot.getValue(Users.class);
                    if(users.getUserId().equals(friend.getFriendId())) {
                        holder.friend.setText(users.getName(friend.getFriendId()));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final Friends f = friend;
        holder.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.itemView.findFocus();
                DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference mNoteRef = mRootRef.child("Users");
                DatabaseReference mPendingRef;
                Friends pendingFriend = new Friends();
                pendingFriend.setFriendId(f.getFriendId());
                pendingFriend.setFriendType("request");
                pendingFriend.setUserId(f.getUserId());
                mPendingRef = mNoteRef.child(f.getFriendId()).child("PendingRequests");
                String id = mPendingRef.push().getKey();
                pendingFriend.setDbId(id);
                mPendingRef.child(id).setValue(pendingFriend);
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
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
