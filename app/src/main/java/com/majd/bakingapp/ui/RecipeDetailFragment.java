package com.majd.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.majd.bakingapp.ItemClickSupport;
import com.majd.bakingapp.R;
import com.majd.bakingapp.adapters.StepAdapter;
import com.majd.bakingapp.model.Ingredient;
import com.majd.bakingapp.model.Recipe;
import com.majd.bakingapp.model.Step;

import java.util.ArrayList;

import static com.majd.bakingapp.ui.RecipeDetailActivity.next;
import static com.majd.bakingapp.ui.RecipeDetailActivity.prev;

/**
 * Created by majd on 9/17/17.
 */

public class RecipeDetailFragment extends Fragment {
    private TextView ingredientsText;
    private RecyclerView stepRecyclerView;
    private LinearLayoutManager layoutManager;
    private StepAdapter stepAdapter;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private String recipeName;
    OnStepClickListener listener;

    public RecipeDetailFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            listener = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_recipe_detail,container,false);
        ingredientsText=(TextView)rootView.findViewById(R.id.ingredients);
        stepRecyclerView=(RecyclerView)rootView.findViewById(R.id.steps_rv);
        if (savedInstanceState!=null){
            ingredients=savedInstanceState.getParcelableArrayList("ingredients");
            steps=savedInstanceState.getParcelableArrayList("steps");

        }

        ingredientsText.append("Ingredients list: \n\n");
       for (int i=0;i<ingredients.size();i++){
           ingredientsText.append((i+1)+":   "+ingredients.get(i).getIngredient()+
                   "("+print(ingredients.get(i).getQuantity())+"   "+
                    ingredients.get(i).getMeasure()+")\n");

       }
       layoutManager=new LinearLayoutManager(getContext());
        stepAdapter=new StepAdapter(getContext(),steps);
        stepRecyclerView.setLayoutManager(layoutManager);
        stepRecyclerView.setAdapter(stepAdapter);
        ItemClickSupport.addTo(stepRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                listener.onStepSelected(position,steps);
            }
        });




        return rootView;


    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }



    public static String print(double x){
        int y=(int)x;
        return (y==x)? String.valueOf(y):String.valueOf(x);
    }

    public interface OnStepClickListener {
        void onStepSelected(int position,ArrayList<Step> steps);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("ingredients",ingredients);
        outState.putParcelableArrayList("steps",steps);
    }
}
