package com.example.monic.moviesearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
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
 * Created by monic on 10/16/2017.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {
    List<Movie> mData;
    Context mContext;
    int mResource;
//    boolean isFavListView;
//    ICallBack callback;
//    ArrayList<Movie> favList;

    public MovieAdapter(Context context, int resource, List<Movie>objects, boolean isFavListView) {
        super(context, resource, objects);
        this.mContext=context;
        this.mResource=resource;
        this.mData=objects;
        //this.isFavListView = isFavListView;
        //callback = (ICallBack) context;
        //favList= new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mResource,parent,false);
        }
        final Movie mu = mData.get(position);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView4);
        TextView movieName = (TextView)convertView.findViewById(R.id.movieName);
        TextView rDate = (TextView)convertView.findViewById(R.id.rDate);
        final ImageButton imageButton = (ImageButton)convertView.findViewById(R.id.favButton);

        movieName.setText(mu.getTitle());
        rDate.setText(mu.getrDate());

        String imageURL =IsNullorEmpty(mu.getImgUrl())?"":mu.getImgUrl().trim();

        if(!IsNullorEmpty(imageURL)){
            Picasso.with(convertView.getContext()).load(imageURL).into(imageView);
        }

//        final SharedPreferences myPrefs = getContext().getSharedPreferences("com.example.monic.moviesearch", MODE_PRIVATE);
//        final SharedPreferences.Editor prefsEditor = myPrefs.edit();
//
//        final String favListString = myPrefs.getString("music_favorites","");
//        final Gson gson = new Gson();
//
//        Type type = new TypeToken<ArrayList<Movie>>(){}.getType();
//
//        ArrayList<Movie> tmp = gson.fromJson(favListString, type);
//
//        tmp = tmp==null ? new ArrayList<Movie>() : tmp;
//
//        favList.clear();
//        if(favList.size()<20) {
//            favList.addAll(tmp);
//        }
//        else
//            Toast.makeText(mContext, "Maximum of 20 favorites", Toast.LENGTH_SHORT).show();
//
//        if(favList.contains(mu))
//            imageButton.setImageResource(R.drawable.goldstar);
//        else
//            imageButton.setImageResource(R.drawable.silverstar);
//
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(favList.contains(mu)) {
//                    favList.remove(mu);
//                    imageButton.setImageResource(R.drawable.silverstar);
//                    Toast.makeText(mContext, "Item removed from favorite", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    if(favList.size()<20) {
//                        favList.add(mu);
//                        imageButton.setImageResource(R.drawable.goldstar);
//                        Toast.makeText(mContext, "Item marked as favorite", Toast.LENGTH_SHORT).show();
//
//                    }
//                    else
//                        Toast.makeText(mContext, "Maximum of 20 favorites", Toast.LENGTH_SHORT).show();
//                }
//                prefsEditor.putString("music_favorites",gson.toJson(favList));
//                prefsEditor.commit();
//
//                if(isFavListView)
//                    callback.refreshFavItems();
//            }
//
//        });

        return convertView;

    }

    private boolean IsNullorEmpty(String str) {
        return (str == null || str.isEmpty());
    }

//    public interface ICallBack {
//        void refreshFavItems();
//    }
}
