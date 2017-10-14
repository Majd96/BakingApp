package com.majd.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by majd on 9/16/17.
 */

public class Recipe implements Parcelable {
    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private  ArrayList<Step> steps;
    private int servings;
    private String image;

    public Recipe(){

    }
    public Recipe(int id,String name,ArrayList<Ingredient> ingredients,
                  ArrayList<Step> steps,int servings,String image){
        this.id=id;this.name=name;
        this.ingredients=ingredients;this.steps=steps;
        this.servings=servings;
        this.image=image;


    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeList(ingredients);
        parcel.writeList(steps);
        parcel.writeString(image);

    }



    private Recipe(Parcel in) {
        id = in.readInt();
       name = in.readString();
        ingredients=new ArrayList<>();
        in.readList(ingredients,Ingredient.class.getClassLoader());
        steps=new ArrayList<>();
        in.readList(steps,Step.class.getClassLoader());
        image=in.readString();

    }


}
