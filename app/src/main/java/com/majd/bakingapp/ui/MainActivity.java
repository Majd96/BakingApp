package com.majd.bakingapp.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.majd.bakingapp.R;
import com.majd.bakingapp.idlingResource.SimpleIdlingResource;
import com.majd.bakingapp.model.Recipe;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.OnRecipeClickListener{
    @Nullable private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource(){
        if(mIdlingResource==null){
            mIdlingResource=new SimpleIdlingResource();
        }
        return mIdlingResource;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIdlingResource();

    }

    @Override
    public void onRecipeSelected(int position, ArrayList<Recipe> recipes) {
        Intent intent=new Intent(this,RecipeDetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putParcelableArrayList("recipes",recipes);
        bundle.putInt("pos",position);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
