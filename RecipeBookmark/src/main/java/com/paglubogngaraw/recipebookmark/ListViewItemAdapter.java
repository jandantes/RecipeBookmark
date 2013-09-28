package com.paglubogngaraw.recipebookmark;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by jan.dantes on 9/27/13.
 */
public class ListViewItemAdapter extends BaseAdapter {
    public String title[], type;
    private int layout, titleId, itemCount;
    public Activity context;
    public LayoutInflater inflater;
    private DatabaseHelper mDatabaseHelper;

    public ListViewItemAdapter(Activity context,int layout,int titleId, int itemCount, String type, String[] title) {
        super();
        this.layout = layout;
        this.context = context;
        this.title = title;
        this.titleId = titleId;
        this.itemCount = itemCount;
        this.type = type;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        mDatabaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM recipes WHERE "+ type + " = '" + title[position] + "'", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(layout, null);
            //holder.imgViewLogo = (ImageView) convertView.findViewById(R.id.imgViewLogo);
            holder.txtViewTitle = (TextView) convertView.findViewById(titleId);
            holder.txtViewCount = (TextView) convertView.findViewById(itemCount);
            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();

        //holder.imgViewLogo.setImageResource(R.drawable.icon);
        holder.txtViewTitle.setText(title[position]);
        holder.txtViewCount.setText(Integer.toString(count));

        return convertView;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder
    {
        //ImageView imgViewLogo;
        TextView txtViewTitle;
        TextView txtViewCount;
    }


}