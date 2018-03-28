package com.example.monic.inclass11;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monic on 11/13/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    List<Contact> mData = new ArrayList<>();

    private static int counter = 0;
    static ContactList activity;



    public ContactAdapter(ContactList contactActivity, List<Contact> mData) {
        this.mData = mData;
        this.activity = contactActivity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        ImageView contactImage;
        ImageView deleteButton;
        ImageView editButton;
        TextView name;
        TextView email;
        TextView phone;
        Contact c;

        public ViewHolder(View v) {
            super(v);
            contactImage = (ImageView) v.findViewById(R.id.contactImage);
            name = (TextView) v.findViewById(R.id.contact_name);
            email = (TextView) v.findViewById(R.id.contact_email);
            phone = (TextView) v.findViewById(R.id.contact_phone);
            deleteButton = (ImageView) v.findViewById(R.id.contact_delete_button);
            editButton = (ImageView) v.findViewById(R.id.edit_profile_button);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Contact conatct = mData.get(position);
        holder.c = conatct;
        holder.name.setText(conatct.getName());
        holder.email.setText(conatct.getEmail());
        holder.phone.setText(conatct.getPhone());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Do you want to delete this contact?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                                DatabaseReference mNoteRef = mRootRef.child("Contacts");
                                mNoteRef.child(c.getUserId()).child(c.getId()).removeValue();
                                Calling listener = (Calling)activity;
                                listener.refresh();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface Calling{
        public void refresh();
    }

}
