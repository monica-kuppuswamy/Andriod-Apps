package com.example.monic.mysocialapp;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendsFragment extends Fragment {

    FirebaseUser user;

    private FirebaseAuth mAuth;


    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    List<String> friendsList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FriendsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FriendsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendsFragment newInstance(String param1, String param2) {
        FriendsFragment fragment = new FriendsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
//        final DatabaseReference mNoteRef = mRootRef.child("Users");
//
//        mAuth = FirebaseAuth.getInstance();
//        user = mAuth.getCurrentUser();
//
//        DatabaseReference friendsNode;
//
//        friendsNode = mNoteRef.child(user.getUid()).child("Friends");
//        friendsNode.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                friendsList = new ArrayList<String>();
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    String f = postSnapshot.getValue(String.class);
//                    friendsList.add(f);
//                }
//                mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_friends_tab);
//                mLayoutManager = new LinearLayoutManager(getActivity());
//                mRecyclerView.setLayoutManager(mLayoutManager);
//                mAdapter = new FriendAdapter((ManageFriends) getActivity(), friendsList);
//                mRecyclerView.setAdapter(mAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference mNoteRef = mRootRef.child("Users");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        DatabaseReference mpendingRef = mNoteRef.child(user.getUid()).child("Friends");
        mpendingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendsList.clear();
                mAuth = FirebaseAuth.getInstance();
                user = mAuth.getCurrentUser();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String f = postSnapshot.getValue(String.class);
                    friendsList.add(f);
                }
                Log.d("demo", "Number of friends " + friendsList.size());
                mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_friends_tab);
                mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new FriendAdapter((ManageFriends) getActivity(), friendsList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}
