package com.example.vicky.justetit.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vicky.justetit.R;
import com.example.vicky.justetit.pojo.Result;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class RecipesListAdapter extends BaseAdapter {

    Context context;
    List<Result> resultList;

    public RecipesListAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    private class ViewHolder {
        ImageView recipeThumbnail;
        TextView recipeTitle;
        TextView recipeIngredients;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.recipes_list_item, null);
            holder = new ViewHolder();
            holder.recipeTitle = (TextView) convertView.findViewById(R.id.recipe_title);
            holder.recipeIngredients = (TextView) convertView.findViewById(R.id.recipe_ingredients);
            holder.recipeThumbnail = (ImageView) convertView.findViewById(R.id.recipe_thumbnail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.recipeTitle.setText(resultList.get(position).getTitle().replaceAll("(\\r|\\n)", ""));
        holder.recipeIngredients.setText(resultList.get(position).getIngredients());
        ImageLoader.getInstance().displayImage(resultList.get(position).getThumbnail(), holder.recipeThumbnail);
        return convertView;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
