package com.example.monic.musicsearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by monic on 10/14/2017.
 */

public class TracksAdapter extends ArrayAdapter<Track> {
    List<Track> mData;
    int mResource;
    Context mContext;
    ArrayList<Track> favList;
    boolean fav = false;
    ICallBack callback;

    public TracksAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Track> objects) {
        super(context, resource, objects);
        this.mData = objects;
        this.mResource = resource;
        this.mContext = context;
        callback = (ICallBack) context;
        if (this.mContext instanceof  MainActivity) {
            fav = true;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }

        final Track t = mData.get(position);
        ImageView image = (ImageView) convertView.findViewById(R.id.imageView4);
        TextView musicName = (TextView) convertView.findViewById(R.id.musicName);
        TextView artistName = (TextView) convertView.findViewById(R.id.artistName);
        final ImageButton favButton = (ImageButton) convertView.findViewById(R.id.favButton);

        musicName.setText(t.getName());
        artistName.setText(t.getArtist());
        Picasso.with(mContext).load(t.getImgUrl()).into(image);

        final SharedPreferences myPrefs = mContext.getSharedPreferences("com.example.monic.musicsearch",
                MODE_PRIVATE);
        final SharedPreferences.Editor prefsEditor = myPrefs.edit();
        final String favListString = myPrefs.getString("music_favorites","");
        final Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Track>>(){}.getType();
        ArrayList<Track> tmp = gson.fromJson(favListString, type);
        tmp = tmp==null ? new ArrayList<Track>() : tmp;



        favList.clear();
        if(favList.size()<20) {
            favList.addAll(tmp);
        } else
            Toast.makeText(mContext, "Maximum of 20 favorites", Toast.LENGTH_SHORT).show();

        if(favList.contains(t)) {
            Log.d("HERE", "HERE");
            favButton.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            Log.d("THERE", "THERE");
            favButton.setImageResource(android.R.drawable.btn_star_big_off);
        }

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favList.contains(t)) {
                    favList.remove(t);
                    favButton.setImageResource(android.R.drawable.btn_star_big_off);
                    Toast.makeText(mContext, "Item removed from favorite", Toast.LENGTH_SHORT).show();
                } else {
                    if (favList.size() < 20) {
                        favList.add(t);
                        favButton.setImageResource(android.R.drawable.btn_star_big_on);
                        Toast.makeText(mContext, "Item marked as favorite", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(mContext, "Maximum of 20 favorites", Toast.LENGTH_SHORT).show();
                }
                prefsEditor.putString("music_favorites", gson.toJson(favList));
                prefsEditor.commit();

                if(fav)
                    callback.refreshFavItems();

            }
        });
        return convertView;
    }

    public interface ICallBack {
        void refreshFavItems();
    }
}