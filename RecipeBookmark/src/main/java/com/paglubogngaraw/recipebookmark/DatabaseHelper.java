package com.paglubogngaraw.recipebookmark;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Patterns;

/**
 * Created by jan.dantes on 9/23/13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_RECIPES = "recipes";
    public static final String COL_ID = "_id";
    public static final String COL_RECIPE_NAME = "recipeName";
    public static final String COL_RECIPE_URL = "recipeUrl";
    public static final String COL_RECIPE_COURSE = "recipeCourse";
    public static final String COL_RECIPE_INGREDIENT = "recipeIngredient";
    private static final String DATABASE_NAME = "recipe_bookmark.db";
    private static final int DATABASE_VERSION = 4;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_RECIPES + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_RECIPE_NAME + " TEXT NOT NULL,"
            + COL_RECIPE_URL + " TEXT NOT NULL,"
            + COL_RECIPE_COURSE + " TEXT,"
            + COL_RECIPE_INGREDIENT + " TEXT"
            + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES + ";");
        onCreate(db);
    }

    public long insert(String tableName, ContentValues values) throws NotValidException{
        validate(values);
        return getWritableDatabase().insert(tableName, null, values);
    }

    public int update(String tableName, long id, ContentValues values) throws NotValidException{
        validate(values);
        String selection = COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        return getWritableDatabase().update(tableName, values,selection,selectionArgs);
    }

    public int delete(String tableName, long id){
        String selection = COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        return getWritableDatabase().delete(tableName, selection, selectionArgs);
    }

    protected void validate(ContentValues values) throws NotValidException{
        if(!values.containsKey(COL_RECIPE_NAME) || values.getAsString(COL_RECIPE_NAME) == null || values.getAsString(COL_RECIPE_NAME).isEmpty()){
            throw new NotValidException("Recipe Name must be set.");
        }
        if(!values.containsKey(COL_RECIPE_URL) || values.getAsString(COL_RECIPE_URL) == null || values.getAsString(COL_RECIPE_URL).isEmpty()){
            throw new NotValidException("Recipe Address must be set.");
        }
        if(!Patterns.WEB_URL.matcher(values.getAsString(COL_RECIPE_URL)).matches()){
            throw new NotValidException("Recipe Address is invalid!");
        }
    }

    public static class NotValidException extends  Throwable {
        public NotValidException(String msg){
            super(msg);
        }
    }

    public Cursor query(String tableName, String orderedBy) {
        String[] projection = {COL_ID, COL_RECIPE_NAME, COL_RECIPE_URL, COL_RECIPE_COURSE, COL_RECIPE_INGREDIENT};
        return getReadableDatabase().query(tableName, projection, null, null, null, null, orderedBy);
    }

    public Cursor category(String recipeCategory, String recipeType) {
        return getReadableDatabase().rawQuery("SELECT * FROM recipes WHERE "+ recipeCategory + " = '" + recipeType + "'", null);
    }


}
