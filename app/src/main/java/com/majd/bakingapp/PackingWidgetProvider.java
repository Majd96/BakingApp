package com.majd.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.majd.bakingapp.adapters.RecipeAdapter;
import com.majd.bakingapp.model.Recipe;
import com.majd.bakingapp.retrofit.RecipeApi;
import com.majd.bakingapp.retrofit.RetrofitBuilder;
import com.majd.bakingapp.ui.MainActivity;
import com.majd.bakingapp.ui.RecipeDetailActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.majd.bakingapp.UpdateWidgetService.startUpdatePlantWidgets;

/**
 * Implementation of App Widget functionality.
 */
public class PackingWidgetProvider extends AppWidgetProvider {
     static Bundle sBundle;
    ArrayList<Recipe> recipes;
     static int postion=0;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String ingredients, Bundle bundle) {






        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.packing_widget_provider);
        Intent intent=new Intent(context, MainActivity.class);
       //intent.putExtras(bundle);


        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
        views.setTextViewText(R.id.appwidget_text,ingredients);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


        Log.d("#####","update");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        startUpdatePlantWidgets(context);


    }

    public static void updatePlantWidgets(Context context, AppWidgetManager appWidgetManager,
                                          String ingredients, int[] appWidgetIds,Bundle bundle) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,ingredients,bundle);
        }
        Log.d("****","update");
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.d("widget is ceated","widget is craeted");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

