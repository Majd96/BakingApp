package com.majd.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.majd.bakingapp.ConfigurationActivity;
import com.majd.bakingapp.ItemClickSupport;
import com.majd.bakingapp.R;
import com.majd.bakingapp.adapters.RecipeAdapter;
import com.majd.bakingapp.idlingResource.SimpleIdlingResource;
import com.majd.bakingapp.model.Recipe;

import com.majd.bakingapp.retrofit.RecipeApi;
import com.majd.bakingapp.retrofit.RetrofitBuilder;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by majd on 9/17/17.
 */

public class MainActivityFragment extends Fragment {


    private static final String TAG=MainActivityFragment.class.getSimpleName();
    private ArrayList<Recipe> recipes;
    public static RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecipeAdapter recipeAdapter;
    Bundle bundle;
    OnRecipeClickListener listener;
    private boolean isTwoPane;

    public MainActivityFragment(){

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         final View viewRoot = inflater.inflate(R.layout.fragment_main_activity, container, false);
        if(savedInstanceState==null){  recipes=new ArrayList<>();}
        else { recipes=savedInstanceState.getParcelableArrayList("recipes");}

        if (viewRoot.findViewById(R.id.twoPane)!=null) isTwoPane=true;
        else isTwoPane=false;
        recyclerView = viewRoot.findViewById(R.id.recipe_rv);

        if(!isTwoPane) {

            linearLayoutManager = new LinearLayoutManager(getContext());

            recyclerView.setLayoutManager(linearLayoutManager);
        }
        else {
            GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3);
            recyclerView.setLayoutManager(gridLayoutManager);
        }


        RecipeApi recipeApi= RetrofitBuilder.retriveRecipeApi();
        final Call<ArrayList<Recipe>> recipe = recipeApi.getRecipe();
        final SimpleIdlingResource idlingResource = (SimpleIdlingResource) ((MainActivity)getActivity()).getIdlingResource();

        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }


        recipe.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {

                recipes=response.body();
                Log.d("&&&&&",recipes.get(0).getName());
                bundle=new Bundle();
                bundle.putParcelableArrayList("recipes",recipes);





                recipeAdapter=new RecipeAdapter(recipes,getContext());

                recyclerView.setAdapter(recipeAdapter);
                if (idlingResource != null) {
                    idlingResource.setIdleState(true);
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.d(TAG,t.getMessage());

            }
        });

        ItemClickSupport.addTo(MainActivityFragment.recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                listener.onRecipeSelected(position,recipes);
            }
        });









        return viewRoot;



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            listener = (OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    public interface OnRecipeClickListener {
        void onRecipeSelected(int position,ArrayList<Recipe> recipes);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("recipes",recipes);
    }
}

