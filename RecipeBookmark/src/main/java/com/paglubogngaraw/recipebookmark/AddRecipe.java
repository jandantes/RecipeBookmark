package com.paglubogngaraw.recipebookmark;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddRecipe extends Activity implements DialogInterface.OnClickListener {

    private EditText recipeName, recipeUrl;
    private DatabaseHelper rec;
    private Spinner coursesSpinner,ingredientSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecipe);
        setTitle("Add New Recipe");

        //define spinners
        coursesSpinner = (Spinner) findViewById(R.id.spinner_recipeCourse);
        ingredientSpinner = (Spinner) findViewById(R.id.spinner_recipeIngredient);

        ArrayAdapter<CharSequence> courseAdapter = ArrayAdapter.createFromResource(this,
                R.array.courses_array, R.layout.spinner_text);
        ArrayAdapter<CharSequence> ingredientAdapter = ArrayAdapter.createFromResource(this,
                R.array.ingredients_array, R.layout.spinner_text);

        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingredientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        coursesSpinner.setAdapter(courseAdapter);
        ingredientSpinner.setAdapter(ingredientAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_recipe, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_addRecipeSave:
                recipeName =  (EditText)findViewById(R.id.txt_recipeName);
                recipeUrl = (EditText)findViewById(R.id.txt_recipeUrl);
                String nameText = recipeName.getText().toString();
                String urlText = recipeUrl.getText().toString();
                String course = coursesSpinner.getSelectedItem().toString();
                String ingredient = ingredientSpinner.getSelectedItem().toString();
                addRecipe(nameText,urlText,course,ingredient);

                break;
            default:
                break;
        }
        return true;
    }


    private void addRecipe(String recipeName,String recipeUrl,String course,String ingredient){
        rec = new DatabaseHelper(this);
        ContentValues values = new ContentValues();
        if(recipeName != null){
            values.put(rec.COL_RECIPE_NAME, recipeName);
        }
        if(recipeUrl != null){
            values.put(rec.COL_RECIPE_URL, recipeUrl);
        }
        values.put(rec.COL_RECIPE_COURSE, course);
        values.put(rec.COL_RECIPE_INGREDIENT, ingredient);
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
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
