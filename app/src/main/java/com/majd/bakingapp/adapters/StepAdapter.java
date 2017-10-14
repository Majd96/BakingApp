package com.majd.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.majd.bakingapp.R;
import com.majd.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by majd on 9/16/17.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHodler> {
    ArrayList<Step> steps;
    Context mContext;

    public StepAdapter(Context context,ArrayList<Step> steps){
        mContext=context;
        this.steps=steps;
    }


    @Override
    public StepViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
         View view=inflater.inflate(R.layout.step_item,parent,false);
        return new StepViewHodler(view);

    }

    @Override
    public void onBindViewHolder(StepViewHodler holder, int position) {
        holder.stepText.setText(position+":  "+steps.get(position).getShortDescription());

    }

    @Override
    public int getItemCount() {

        if (steps==null)return 0;
        else return steps.size();
    }

    public class StepViewHodler extends RecyclerView.ViewHolder {
        TextView stepText;


        public StepViewHodler(View itemView) {

            super(itemView);
            stepText=itemView.findViewById(R.id.step);
        }
    }
}
