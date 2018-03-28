package com.example.monic.foodrecipe;

import android.os.AsyncTask;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by monic on 10/2/2017.
 */

public class GetRecipeAsyncTask extends AsyncTask<String, Void, ArrayList<Recipe>> {

    IData activity;

    public GetRecipeAsyncTask(IData activity) {
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(ArrayList<Recipe> recipes) {
        super.onPostExecute(recipes);
        activity.setupData(recipes);
    }

    @Override
    protected ArrayList<Recipe> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();
                return RecipeParser.RecipeSAXParser.parseRecipe(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }


    public interface IData {
        void setupData(ArrayList<Recipe> s);
    }
}
