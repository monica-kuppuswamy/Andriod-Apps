package com.example.monic.foodrecipe;

import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by monic on 10/2/2017.
 */

public class RecipeParser {
    public static class RecipeSAXParser extends DefaultHandler {

        public static ArrayList<Recipe> parseRecipe(InputStream in) throws IOException, SAXException, XmlPullParserException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            Recipe recipe = null;
            ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
            int event = parser.getEventType();
            while(event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("recipe")) {
                            recipe = new Recipe();
                        } else if(parser.getName().equals("title")) {
                            recipe.setTitle(parser.nextText().trim());
                        } else if(parser.getName().equals("href")) {
                            recipe.setUrl(parser.nextText().trim());
                        } else if(parser.getName().equals("ingredients")) {
                            recipe.setIngredients(parser.nextText().trim());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("recipe")) {
                            recipeList.add(recipe);
                        }
                }
                event = parser.next();
            }
            return recipeList;
        }
    }
}
