package com.paglubogngaraw.recipebookmark;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by jan.dantes on 9/27/13.
 */
public class ListViewItemAdapter extends BaseAdapter {
    public String title[];
    private int layout, titleId;
    //public String description[];
    public Activity context;
    public LayoutInflater inflater;

    //public ListViewItemAdapter(Activity context,String[] title, String[] description) {
    public ListViewItemAdapter(Activity context,int layout,int titleId, String[] title) {
        super();
        this.layout = layout;
        this.context = context;
        this.title = title;
        this.titleId = titleId;
        //this.description = description;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        //TextView txtViewDescription;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(layout, null);
            //holder.imgViewLogo = (ImageView) convertView.findViewById(R.id.imgViewLogo);
            holder.txtViewTitle = (TextView) convertView.findViewById(titleId);
            //holder.txtViewDescription = (TextView) convertView.findViewById(R.id.txtViewDescription);
            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();

        //holder.imgViewLogo.setImageResource(R.drawable.icon);
        holder.txtViewTitle.setText(title[position]);
        //holder.txtViewDescription.setText(description[position]);

        return convertView;
    }

}