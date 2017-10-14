package com.majd.bakingapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.majd.bakingapp.R;
import com.majd.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by majd on 9/16/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private ArrayList<Recipe> recipes;
    Context mContext;

    public RecipeAdapter(ArrayList<Recipe> recipes,Context context)
    {
        this.recipes=recipes;
        mContext=context;
    }


    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recipe_item,parent,false);
        RecipeViewHolder holder=new RecipeViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        if(recipes==null) return 0;
        else return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder


    {
        TextView recipeName;
        TextView recipeServings;
        ImageView recipeImage;
        public RecipeViewHolder(View itemView) {
            super(itemView);

            recipeName= itemView.findViewById(R.id.recipe_name);
            recipeServings=itemView.findViewById(R.id.recipe_servings);
            recipeImage=itemView.findViewById(R.id.recipe_icon);

        }

        public void bind(int position){
            String name=recipes.get(position).getName();
            int servings=recipes.get(position).getServings();
            String imageUrl=recipes.get(position).getImage();
            recipeName.setText(name);
            recipeServings.setText("Servings: "+String.valueOf(servings));
            if(imageUrl!=""){
                Uri uri=Uri.parse(imageUrl).buildUpon().build();
                Picasso.with(mContext).load(uri).into(recipeImage);

            }









        }
    }
}
