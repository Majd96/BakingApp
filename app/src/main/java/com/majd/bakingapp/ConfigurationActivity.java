package com.majd.bakingapp;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import com.majd.bakingapp.adapters.RecipeAdapter;
import com.majd.bakingapp.adapters.StepAdapter;
import com.majd.bakingapp.model.Ingredient;
import com.majd.bakingapp.model.Recipe;
import com.majd.bakingapp.retrofit.RecipeApi;
import com.majd.bakingapp.retrofit.RetrofitBuilder;
import com.majd.bakingapp.ui.MainActivityFragment;
import com.majd.bakingapp.ui.RecipeDetailFragment;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

/**
 * Created by majd on 9/20/17.
 */

public class ConfigurationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private ArrayList<Recipe> recipes;

    private int widgetId;
     private int recipeId;
    private final static String TAG=ConfigurationActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            recipeId=savedInstanceState.getInt("recipeId");
            widgetId=savedInstanceState.getInt(EXTRA_APPWIDGET_ID);
            recipes=savedInstanceState.getParcelableArrayList("recipes");
        }
        setContentView(R.layout.activity_configuration);
        //if the user hit the return button or cancel the config activity it wont add the widget

        recyclerView=(RecyclerView)findViewById(R.id.recipe_rv_config);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        RecipeApi recipeApi= RetrofitBuilder.retriveRecipeApi();
        final Call<ArrayList<Recipe>> recipe = recipeApi.getRecipe();


        recipe.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {

                recipes=response.body();




                recipeAdapter=new RecipeAdapter(recipes,getApplicationContext());
                recyclerView.setAdapter(recipeAdapter);



            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.d(TAG,t.getMessage());


            }
        });

        setResult(RESULT_CANCELED);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showWidgwt(position);

                SharedPreferences sp = getSharedPreferences("myPref", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("desiredPos",position);
                editor.commit();


            }
        });






    }


    private void showWidgwt(int position){
        //first we want to extract the widget id

        widgetId= AppWidgetManager.INVALID_APPWIDGET_ID;
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            widgetId=bundle.getInt(EXTRA_APPWIDGET_ID,INVALID_APPWIDGET_ID);

            String recipeIngredient="";

                ArrayList<Ingredient> ingredients = recipes.get(position).getIngredients();
                for (int i=0;i<ingredients.size();i++){
                    recipeIngredient+="Ingredients list: \n\n"+(i+1)+":   "+ingredients.get(i).getIngredient()+
                            "("+ RecipeDetailFragment.print(ingredients.get(i).getQuantity())+"   "+
                            ingredients.get(i).getMeasure()+")\n";
                }

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

            int [] array=new int[1];
            array[0]=widgetId;
            Bundle bundle1=new Bundle();
            bundle1.putParcelableArrayList("recipes",recipes);
            bundle1.putInt("pos",position);


            PackingWidgetProvider.updatePlantWidgets(this, appWidgetManager,recipeIngredient,array,bundle1);



           setResult(RESULT_OK);
            finish();
        }
        if (widgetId == INVALID_APPWIDGET_ID) {

            finish();
        }

        }





    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelableArrayList("recipes",recipes);
        outState.putInt(EXTRA_APPWIDGET_ID,widgetId);
        outState.putInt("recipeId",recipeId);
    }
}
