package com.majd.bakingapp.retrofit;

import com.majd.bakingapp.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by majd on 9/17/17.
 */

public interface RecipeApi {

    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipe();

}
