package com.goddardlabs.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

class ImageAdapter extends BaseAdapter {
    private final Context context;
    private final Movie[] movies;

    public ImageAdapter(Context context, Movie[] movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return this.movies.length;
    }

    @Override
    public Movie getItem(int position) {
        return this.movies[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;

        if (convertView == null) {
            imageView = new ImageView(this.context);
            imageView.setAdjustViewBounds(true);
        }

        Picasso.with(this.context)
                .load(this.movies[position].getPoster_path())
                .placeholder(R.drawable.movieticket)
                .resize(1280, 1920)
                .error(R.drawable.awesome)
                .into(imageView);

        return imageView;
    }
}

