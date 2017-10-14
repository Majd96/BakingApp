package com.majd.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.majd.bakingapp.R;
import com.majd.bakingapp.model.Ingredient;
import com.majd.bakingapp.model.Recipe;
import com.majd.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * Created by majd on 9/17/17.
 */

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnStepClickListener {
    private  ArrayList<Recipe> recipes;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private int position;
    private String recipeName;
    FragmentManager fragmentManager;
    public static ImageButton prev;
    public static ImageButton next;
    private static int currentstep;
    Boolean isTwoPane;
    RecipeDetailFragment recipeDetailFragment;
    StepDetailFragment stepDetailFragment;
    int prevState=View.INVISIBLE;
    int nextState=View.INVISIBLE;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Intent intent=getIntent();
        prev=(ImageButton)findViewById(R.id.imageButtonPrev);
        next=(ImageButton)findViewById(R.id.imageButtonNext);


        if(findViewById(R.id.linear_layout_twoPane)==null) isTwoPane=false;
        else isTwoPane=true;
        if(!isTwoPane){
            prev.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
        }
        if(savedInstanceState==null) {


            Bundle bundle = intent.getExtras();
            recipes = bundle.getParcelableArrayList("recipes");
            position = bundle.getInt("pos");
            recipeName=recipes.get(position).getName();
            ingredients=recipes.get(position).getIngredients();
            steps=recipes.get(position).getSteps();



            recipeDetailFragment=new RecipeDetailFragment();
            recipeDetailFragment.setIngredients(ingredients);
            recipeDetailFragment.setSteps(steps);
            recipeDetailFragment.setRecipeName(recipeName);
            getSupportActionBar().setTitle(recipeName);
            fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.detail_fragment_container,recipeDetailFragment).commit();

            if(isTwoPane){

               stepDetailFragment=new StepDetailFragment();
                fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.detail_fragment_container_stepDetail,stepDetailFragment).commit();
                stepDetailFragment.setStep(steps.get(position));
            }



        }
        else {
            recipes =savedInstanceState.getParcelableArrayList("recipes");
            position = savedInstanceState.getInt("pos");
            steps=savedInstanceState.getParcelableArrayList("steps");

        }









    }


    @Override
    public void onStepSelected(int position, ArrayList<Step> steps) {
        if(!isTwoPane){
        stepDetailFragment=new StepDetailFragment();
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.detail_fragment_container,stepDetailFragment).commit();
        stepDetailFragment.setStep(steps.get(position));
        }
        else {
             stepDetailFragment=new StepDetailFragment();
            fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.detail_fragment_container_stepDetail,stepDetailFragment).commit();
            stepDetailFragment.setStep(steps.get(position));

        }

        currentstep=position;
        Log.d("first time&&&",currentstep+"");
        if(!isTwoPane) {
            prev.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelableArrayList("recipes",recipes);
        outState.putInt("pos",position);
        outState.putParcelableArrayList("steps",steps);

    }

    public void next(View view) {
        if(currentstep!=steps.size()-1) {

            stepDetailFragment = new StepDetailFragment();
            if(stepDetailFragment.simpleExoPlayer!=null){
                stepDetailFragment.simpleExoPlayer.stop();
            }
            currentstep += 1;
            stepDetailFragment.setStep(steps.get(currentstep));
            fragmentManager.beginTransaction().replace(R.id.detail_fragment_container, stepDetailFragment).commit();
            Toast.makeText(this,"Step "+String.valueOf(currentstep),Toast.LENGTH_LONG).show();


            Log.d("next time&&&", currentstep + "");
        }
        else {
            Toast.makeText(this,"This is the last step",Toast.LENGTH_LONG).show();

        }

    }

    public void prev(View view) {
        if(currentstep!=0) {

            stepDetailFragment = new StepDetailFragment();
            if(stepDetailFragment.simpleExoPlayer!=null){
                stepDetailFragment.simpleExoPlayer.stop();
            }
            currentstep -= 1;
            stepDetailFragment.setStep(steps.get(currentstep));
            fragmentManager.beginTransaction().replace(R.id.detail_fragment_container, stepDetailFragment).commit();
            Toast.makeText(this,"Step "+String.valueOf(currentstep),Toast.LENGTH_LONG).show();

            Log.d("prev time&&&", currentstep + "");
        }
        else {
            Toast.makeText(this,"No previous steps",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               if(isTwoPane){
                   Intent intent=new Intent(this,MainActivity.class);
                   startActivity(intent);
               }
               else{


                    try {if(recipeDetailFragment.isVisible()){
                        Intent intent=new Intent(this,MainActivity.class);
                        startActivity(intent);

                    }

                    else  {
                        recipeDetailFragment=new RecipeDetailFragment();
                        recipeDetailFragment.setIngredients(ingredients);
                        recipeDetailFragment.setSteps(steps);
                        recipeDetailFragment.setRecipeName(recipeName);
                        getSupportActionBar().setTitle(recipeName);
                        fragmentManager=getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.detail_fragment_container,recipeDetailFragment).commit();
                        prev.setVisibility(View.INVISIBLE);
                        next.setVisibility(View.INVISIBLE);

                    }

                    }catch (NullPointerException e){
                    Log.d("NullPointerException","NullPointerException");

                    }

               }

                break;
        }
        return true;
    }




}
