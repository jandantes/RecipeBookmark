package com.paglubogngaraw.recipebookmark;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

//https://code.google.com/p/android-query/
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.XmlDom;

import java.util.List;

/**
 * Created by jan.dantes on 9/25/13.
 */
public class RecipeItemAdapter extends SimpleCursorAdapter {
    private Context mContext;
    private int layout;
    private Cursor cr;
    private LayoutInflater inflater;
    private AQuery aq;

    String queryUrl = "http://api.flickr.com/services/rest/?method=flickr.photos.search";
    String queryPerPage = "&per_page=1";
    String queryFormat = "&format=rest";
    String queryTags = "&tags=food,recipe,";
    String querySort = "&sort=relevance";
    String queryKey = "&api_key=";
    String queryText = "&text=";
    String queryContentType = "&content_type=1";
    String apiKey = "";

    public RecipeItemAdapter(Context context,int layout, Cursor c,String[] from,int[] to)  {
        super(context, layout, c, from, to, 0);
        this.layout = layout;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.cr = c;
    }

    @Override
    public void bindView(final View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        aq = new AQuery(view);

        TextView name = (TextView)view.findViewById(R.id.txt_recipeName);
        TextView url = (TextView)view.findViewById(R.id.txt_recipeUrl);
        TextView course = (TextView)view.findViewById(R.id.txt_recipeCourse);
        TextView ingredient = (TextView)view.findViewById(R.id.txt_recipeIngredient);

        String recipeName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_RECIPE_NAME));
        String recipeIngredient = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_RECIPE_INGREDIENT));
        name.setText(recipeName);
        url.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_RECIPE_URL)));
        course.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_RECIPE_COURSE)));
        ingredient.setText(recipeIngredient);

        aq.id(view.findViewById(R.id.image_recipeThumbnail)).clear();

        String imageUrl = queryUrl + queryPerPage + queryFormat + queryKey + apiKey + queryContentType + querySort + queryTags + recipeIngredient + queryText + recipeName;
        aq.ajax(imageUrl, XmlDom.class, new AjaxCallback<XmlDom>(){
            public void callback(String url, XmlDom xml, AjaxStatus status){
                List<XmlDom> entry = xml.tags("photo");
                String imagePath = null;
                for(XmlDom item: entry){
                    String id = item.attr("id");
                    String farm = item.attr("farm");
                    String secret = item.attr("secret");
                    String server = item.attr("server");
                    imagePath = "http://farm" + farm + ".static.flickr.com/" + server +"/" + id + "_" + secret + "_m.jpg";
                }
                String cachedImage =  imagePath;
                Bitmap preset = aq.getCachedImage(cachedImage);
                //Log.d(preset.toString(),"preset");
                aq.id(view.findViewById(R.id.image_recipeThumbnail)).progress(view.findViewById(R.id.progress)).image(imagePath, true, true, 200, 0, preset, AQuery.FADE_IN);
                /*
                aq.id(view.findViewById(R.id.image_recipeThumbnail)).progress(view.findViewById(R.id.progress)).image(imagePath, true, true, 200, 0, new BitmapAjaxCallback(){
                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){
                        iv.setImageBitmap(bm);
                    }
                });
                */
            }
        });
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }


}

