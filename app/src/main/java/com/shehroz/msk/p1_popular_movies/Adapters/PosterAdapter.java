package com.shehroz.msk.p1_popular_movies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.shehroz.msk.p1_popular_movies.Activities.MainActivity;
import com.shehroz.msk.p1_popular_movies.R;
import com.squareup.picasso.Picasso;

/**
 * Created by DELL on 8/11/2016.
 */
public class PosterAdapter extends BaseAdapter {

    private Context mContext;

    public PosterAdapter(Context c){
        mContext=c;
    }

    @Override
    public int getCount() {
        return MainActivity.images.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView poster;
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            poster = (ImageView) inflater.inflate(R.layout.grid_item_movie_poster, parent, false);
        }else{
            poster = (ImageView) convertView;
        }

        Picasso.with(mContext).load(MainActivity.images.get(position)).into(poster);

        return poster;
    }
}
