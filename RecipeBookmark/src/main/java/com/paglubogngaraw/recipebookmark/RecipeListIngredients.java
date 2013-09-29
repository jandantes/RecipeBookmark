package com.paglubogngaraw.recipebookmark;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class RecipeListIngredients extends Activity {

    private DatabaseHelper mDatabaseHelper;
    private ListView recipeList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipelist);
        String newTitle = getIntent().getStringExtra("ingredients");
        setTitle(newTitle);

        recipeList = (ListView) findViewById(R.id.listView_recipeList);

        String[] from = new String[]{
                DatabaseHelper.COL_RECIPE_NAME,
                DatabaseHelper.COL_RECIPE_URL,
                DatabaseHelper.COL_RECIPE_COURSE,
                DatabaseHelper.COL_RECIPE_INGREDIENT
        };
        int[] to = {
                R.id.txt_recipeName,
                R.id.txt_recipeUrl,
                R.id.txt_recipeCourse,
                R.id.txt_recipeIngredient
        };

        mDatabaseHelper = new DatabaseHelper(this);
        Cursor c = mDatabaseHelper.category("recipeIngredient", newTitle);
        final RecipeItemAdapter adapter = new RecipeItemAdapter(RecipeListIngredients.this, R.layout.listview_row, c, from, to);
        recipeList.setEmptyView(findViewById(R.id.emptyList));
        recipeList.setAdapter(adapter);
        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView name = (TextView) view.findViewById(R.id.txt_recipeName);
                TextView url = (TextView) view.findViewById(R.id.txt_recipeUrl);
                String itemUrl = url.getText().toString();
                String itemName = name.getText().toString();
                Intent viewRecipe = new Intent(RecipeListIngredients.this, ViewRecipe.class);
                viewRecipe.putExtra("url", itemUrl);
                viewRecipe.putExtra("name", itemName);
                startActivity(viewRecipe);
            }
        });


    }
}
