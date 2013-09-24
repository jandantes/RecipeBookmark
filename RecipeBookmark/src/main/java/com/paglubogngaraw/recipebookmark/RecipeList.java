package com.paglubogngaraw.recipebookmark;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class RecipeList extends Activity {
    private DatabaseHelper mDatabaseHelper;
    private ListView listView_recipeList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipelist);

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

        Cursor c = mDatabaseHelper.query(DatabaseHelper.TABLE_RECIPES,DatabaseHelper.COL_RECIPE_NAME);
        final RecipeItemAdapter adapter = new RecipeItemAdapter(this, R.layout.listview_row, c, from, to);
        listView_recipeList = (ListView) findViewById(R.id.listView_recipeList);
        listView_recipeList.setAdapter(adapter);
        listView_recipeList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView_recipeList.setSelector(R.drawable.list_selector);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_addRecipe:
                Intent loadAddRecipe = new Intent(RecipeList.this, AddRecipe.class);
                RecipeList.this.startActivity(loadAddRecipe);
                break;
            default:
                break;
        }
        return true;
    }
}
