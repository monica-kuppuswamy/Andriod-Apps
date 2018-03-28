package com.example.monic.foodrecipe;

import java.io.Serializable;

/**
 * Created by monic on 10/2/2017.
 */

public class Recipe implements Serializable {
    String title;
    String ingredients;
    String url;


    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {

        return title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getUrl() {
        return url;
    }
}
