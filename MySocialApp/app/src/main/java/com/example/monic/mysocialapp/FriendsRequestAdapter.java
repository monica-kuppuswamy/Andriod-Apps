package com.example.monic.mysocialapp;

import android.content.DialogInterface;
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

public class FriendsRequestAdapter extends RecyclerView.Adapter<FriendsRequestAdapter.ViewHolder> {

    List<String> mData = new ArrayList<String>();

    private static int counter = 0;
    static ManageFriends mainActivity;

    DatabaseReference mNoteRef;
    DatabaseReference mFriendRef1;
    DatabaseReference mFriendRef2;

    public FriendsRequestAdapter(ManageFriends mainActivity, List<String> mData) {
        this.mData = mData;
        this.mainActivity = mainActivity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView friend;
        ImageView buttonAccept;
        ImageView buttonReject;

        public ViewHolder(View v) {
            super(v);
            friend = v.findViewById(R.id.newfriendReqAddName);
            buttonAccept = v.findViewById(R.id.acceptButton);
            buttonReject = v.findViewById(R.id.declineButton);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_request_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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
//        holder.buttonAccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
//                mNoteRef = mRootRef.child("Users");
//                mFriendRef1 = mNoteRef.child(f.getUserId()).child("Friends");
//                mFriendRef2 = mNoteRef.child(f.getFriendId()).child("Friends");
//
//                String id1 = mFriendRef1.push().getKey();
//                String id2 = mFriendRef2.push().getKey();
//                mFriendRef1.child(id1).setValue(f.getFriendId());
//                mFriendRef2.child(id2).setValue(f.getUserId());
//
//                DatabaseReference mPendingRef = mNoteRef.child(f.getFriendId()).child("PendingRequests");
//                mPendingRef.child(f.getDbId()).removeValue();
//            }
//        });
//
//        holder.buttonReject.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mainActivity);
//                builder.setTitle("Do you want to reject friend request?")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int which) {
//                                DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
//                                DatabaseReference mNoteRef = mRootRef.child("Users");
//                                DatabaseReference mPendingRef = mNoteRef.child(f.getFriendId()).child("PendingRequests");
//                                mPendingRef.child(f.getDbId()).removeValue();
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int which) {
//                            }
//                        });
//                final android.app.AlertDialog alert = builder.create();
//                alert.show();
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
}
