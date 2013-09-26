package com.paglubogngaraw.recipebookmark;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class RecipeList extends Fragment{

    private DatabaseHelper mDatabaseHelper;
    private ListView recipeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_recipelist,container, false);
        recipeList = (ListView) rootView.findViewById(R.id.listView_recipeList);
        return rootView;
    }

    @Override
    public  void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

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

        //DatabaseHelper.java
        mDatabaseHelper = new DatabaseHelper(getActivity());

        //Cursor - DatabaseHelper.java(Table name, Table column sorter)
        Cursor c = mDatabaseHelper.query(DatabaseHelper.TABLE_RECIPES,DatabaseHelper.COL_RECIPE_NAME);

        //Custom adapter: list view with multiple TextViews
        final RecipeItemAdapter adapter = new RecipeItemAdapter(getActivity(), R.layout.listview_row, c, from, to);

        //ListView using RecipeItemAdapter
        recipeList.setAdapter(adapter);
        recipeList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        recipeList.setSelector(R.drawable.list_selector);
        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView,final View view,final int i,final long l) {
                TextView name = (TextView) view.findViewById(R.id.txt_recipeName);
                TextView url = (TextView) view.findViewById(R.id.txt_recipeUrl);
                String itemUrl = url.getText().toString();
                String itemName = name.getText().toString();
                //Toast.makeText(getApplicationContext(), "View: " + item, Toast.LENGTH_LONG).show();
                Intent viewRecipe = new Intent(getActivity(), ViewRecipe.class);
                viewRecipe.putExtra("url", itemUrl);
                viewRecipe.putExtra("name", itemName);
                startActivity(viewRecipe);
            }
        });

        recipeList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int i, final long l) {
                TextView itemName = (TextView) view.findViewById(R.id.txt_recipeName);
                final String newItemName = itemName.getText().toString();

                view.setSelected(true);
                view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

                getActivity().startActionMode(new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                        MenuInflater inflater = actionMode.getMenuInflater();
                        inflater.inflate(R.menu.recipe_list_contextual, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode actionMode, MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_deleteRecipe:
                                //Toast.makeText(getApplicationContext(), "Long press: " + l, Toast.LENGTH_SHORT).show();
                                new AlertDialog.Builder(getActivity())
                                        .setTitle(getString(R.string.title_dialog_delete_recipe))
                                        .setMessage(getString(R.string.message_delete) + newItemName + "?")
                                        .setIcon(R.drawable.ic_dialog_warning)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int whichButton){
                                                deleteRecipe(l);
                                                getActivity().recreate();
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null).show();
                                actionMode.finish();
                                return true;
                            default:
                                return false;
                        }
                    }
                    @Override
                    public void onDestroyActionMode(ActionMode actionMode) {
                        view.setSelected(false);
                        view.setBackgroundColor(getResources().getColor(android.R.color.white));
                    }
                });
                return true;
            }
        });

    }
    private void deleteRecipe(Long id){
        mDatabaseHelper = new DatabaseHelper(getActivity());
        mDatabaseHelper.delete(DatabaseHelper.TABLE_RECIPES, id);
        mDatabaseHelper.close();
    }
}
