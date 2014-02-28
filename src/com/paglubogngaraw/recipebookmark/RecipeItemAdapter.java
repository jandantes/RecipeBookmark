package com.paglubogngaraw.recipebookmark;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by jan.dantes on 9/25/13.
 */
public class RecipeItemAdapter extends SimpleCursorAdapter {
    private int layout;
    private LayoutInflater inflater;

    public RecipeItemAdapter(Context context,int layout, Cursor c,String[] from,int[] to)  {
        super(context,layout,c,from,to,0);
        this.layout = layout;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        TextView name = (TextView)view.findViewById(R.id.txt_recipeName);
        TextView url = (TextView)view.findViewById(R.id.txt_recipeUrl);
        TextView course = (TextView)view.findViewById(R.id.txt_recipeCourse);
        TextView ingredient = (TextView)view.findViewById(R.id.txt_recipeIngredient);

        name.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_RECIPE_NAME)));
        url.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_RECIPE_URL)));
        course.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_RECIPE_COURSE)));
        ingredient.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_RECIPE_INGREDIENT)));

    }
    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }
}

