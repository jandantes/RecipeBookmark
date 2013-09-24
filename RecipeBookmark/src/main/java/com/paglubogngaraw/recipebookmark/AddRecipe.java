package com.paglubogngaraw.recipebookmark;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddRecipe extends Activity implements DialogInterface.OnClickListener {
    //private DatabaseHelper mDatabaseHelper;
    private EditText recipeName, recipeUrl, recipeNotes;

    private DatabaseHelper rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecipe);
        setTitle("Add New Recipe");
        //mDatabaseHelper = new DatabaseHelper(this);


        recipeName =  (EditText)findViewById(R.id.txt_recipeName);
        recipeUrl = (EditText)findViewById(R.id.txt_recipeUrl);
        recipeNotes = (EditText)findViewById(R.id.txt_recipeNotes);
        final Button addNewRecipe = (Button)findViewById(R.id.btn_saveButton);

        addNewRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = recipeName.getText().toString();
                String urlText = recipeUrl.getText().toString();
                String notesText = recipeNotes.getText().toString();
                addRecipe(nameText,urlText ,notesText);
            }
        });
    }

    private void addRecipe(String recipeName,String recipeUrl, String recipeNotes){
        rec = new DatabaseHelper(this);
        ContentValues values = new ContentValues();
        if(recipeName != null){
            values.put(rec.COL_RECIPE_NAME, recipeName);
        }
        if(recipeUrl != null){
            values.put(rec.COL_RECIPE_URL, recipeUrl);
        }
        values.put(rec.COL_RECIPE_NOTES, recipeNotes);
        try {
            rec.insert(DatabaseHelper.TABLE_RECIPES, values);
            Intent loadRecipeList = new Intent(AddRecipe.this, RecipeList.class);
            loadRecipeList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            AddRecipe.this.startActivity(loadRecipeList);
        }catch (DatabaseHelper.NotValidException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_recipe, menu);
        return true;
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
