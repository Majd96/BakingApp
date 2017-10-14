package com.majd.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by majd on 9/16/17.
 */

public class Ingredient implements Parcelable {

    private double quantity;
    private String measure;
    private String ingredient;


    public Ingredient() {

    }

    public Ingredient(double quantity,String measure,String ingredient){
        this.quantity=quantity;
        this.measure=measure;
        this.ingredient=ingredient;
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public double getQuantity() {
        return quantity;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);

    }

    private Ingredient(Parcel in){
        quantity=in.readDouble();
        measure=in.readString();
        ingredient=in.readString();
    }
}

