package com.paglubogngaraw.recipebookmark;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by jan.dantes on 9/25/13.
 */
public class RecipeCourseHelper extends SQLiteOpenHelper {
    public static final String TABLE_RECIPE_COURSE = "course";
    public static final String COL_ID = BaseColumns._ID;
    public static final String COL_RECIPE_COURSE = "recipeCourse";
    private static final String DATABASE_NAME = "recipe_course.db";
    private static final int DATABASE_VERSION = 1;

    public RecipeCourseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_RECIPE_COURSE + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_RECIPE_COURSE + " TEXT NOT NULL"
            + ");"
        );
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_COURSE + ";");
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
        if(!values.containsKey(COL_RECIPE_COURSE) || values.getAsString(COL_RECIPE_COURSE) == null || values.getAsString(COL_RECIPE_COURSE).isEmpty()){
            throw new NotValidException("Recipe Course must be set");
        }
    }

    @SuppressWarnings("serial")
    public static class NotValidException extends  Throwable {
        public NotValidException(String msg){
            super(msg);
        }
    }

    public Cursor query(String tableName, String orderedBy) {
        String[] projection = {COL_ID, COL_RECIPE_COURSE};
        return getReadableDatabase().query(tableName, projection, null, null, null, null, orderedBy);
    }
}
