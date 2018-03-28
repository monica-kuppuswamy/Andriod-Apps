package com.example.monic.mysocialapp;


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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFriendsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFriendsFragment extends Fragment {

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    List<String> friendList = new ArrayList<String>();
    FirebaseUser user;
    Users u;

    private FirebaseAuth mAuth;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mNoteRef = mRootRef.child("Users");
    DatabaseReference mpendingRef;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddFriendsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFriendsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFriendsFragment newInstance(String param1, String param2){
        AddFriendsFragment fragment = new AddFriendsFragment();
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
        mNoteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    u = postSnapshot.getValue(Users.class);
                    mAuth = FirebaseAuth.getInstance();
                    user = mAuth.getCurrentUser();
                    if(!(u.getUserId().equals(user.getUid()))) {
                        friendList.add(u.getUserId());

//                        /* Checking if the user has already sent friend request to other user and not
//                        *  removing it from the friendsList*/
//                        mpendingRef = mNoteRef.child(u.getUserId()).child("PendingRequests");
//                        mpendingRef.addValueEventListener(new ValueEventListener() {
//
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                mAuth = FirebaseAuth.getInstance();
//                                user = mAuth.getCurrentUser();
//                                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                                    String s = postSnapshot.getValue(String.class);
//                                    if(s.equals(user.getUid())) {
//                                        if(friendList.contains(u.getUserId())) {
//                                            friendList.remove(u.getUserId());
//                                            break;
//                                        }
//                                    }
//                                }
//                                mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.new_friends_tab);
//                                mLayoutManager = new LinearLayoutManager(getActivity());
//                                mRecyclerView.setLayoutManager(mLayoutManager);
//                                mAdapter = new NewFriendsAdapter((ManageFriends) getActivity(), friendList);
//                                mRecyclerView.setAdapter(mAdapter);
//                                mAdapter.notifyDataSetChanged();
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                            }
//                        });
//                    }
//                }
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    u = postSnapshot.getValue(Users.class);
//                    if((u.getUserId().equals(user.getUid()))) {
//                        mpendingRef = mNoteRef.child(u.getUserId()).child("PendingRequests");
//                        mpendingRef.addValueEventListener(new ValueEventListener() {
//
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                Log.d("Demo","checking");
//                                mAuth = FirebaseAuth.getInstance();
//                                user = mAuth.getCurrentUser();
//                                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                                    String s = postSnapshot.getValue(String.class);
//                                    Log.d("Demo","s is" + s);
//                                    if(friendList.contains(s)) {
//                                        friendList.remove(s);
//                                        break;
//                                    }
//                                }
//                                mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.new_friends_tab);
//                                mLayoutManager = new LinearLayoutManager(getActivity());
//                                mRecyclerView.setLayoutManager(mLayoutManager);
//                                mAdapter = new NewFriendsAdapter((ManageFriends) getActivity(), friendList);
//                                mRecyclerView.setAdapter(mAdapter);
//                                mAdapter.notifyDataSetChanged();
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                            }
//                        });
                    }
                }

                mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.new_friends_tab);
                mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new NewFriendsAdapter((ManageFriends) getActivity(), friendList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_friends, container, false);
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
