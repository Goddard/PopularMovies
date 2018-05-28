package com.goddardlabs.popularmovies;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MoviesDetailsActivity extends AppCompatActivity {
    private final String TAG = MoviesDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        final ConstraintLayout constraint_layout_main_activity = findViewById(R.id.activity_background);
        ImageView image_view_poster = findViewById(R.id.imageview_poster);
        TextView text_view_overview = findViewById(R.id.textview_overview);
        text_view_overview.setBackgroundColor(getColor(R.color.colorPrimaryDark));
        TextView text_view_rating = findViewById(R.id.textview_rating);
        text_view_rating.setBackgroundColor(getColor(R.color.colorPrimaryDark));
        TextView tv_rating_title = findViewById(R.id.textview_rating_title);
        tv_rating_title.setBackgroundColor(getColor(R.color.colorPrimaryDark));
        TextView text_view_release_date = findViewById(R.id.textview_release_date);
        text_view_release_date.setBackgroundColor(getColor(R.color.colorPrimaryDark));
        TextView text_view_release_date_title = findViewById(R.id.textview_release_date_title);
        text_view_release_date_title.setBackgroundColor(getColor(R.color.colorPrimaryDark));

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(getString(R.string.movie_type_key));

        final ImageView img = new ImageView(this);
        Picasso.with(this)
                .load(movie.getBack_drop_path())
                .error(R.drawable.awesome)
                .placeholder(R.drawable.movieticket)
                .into(img, new Callback() {
                    @Override
                    public void onSuccess() {

                        constraint_layout_main_activity.setBackground(img.getDrawable());
                    }

                    @Override
                    public void onError() {

                    }});

        this.setTitle(movie.getTitle());

        Picasso.with(this)
                .load(movie.getPoster_path())
                .resize(250,
                        325)
                .error(R.drawable.awesome)
                .placeholder(R.drawable.movieticket)
                .into(image_view_poster);

        String overView = movie.getOverview();
        if (overView == null) {
            text_view_overview.setTypeface(null, Typeface.ITALIC);
            overView = getString(R.string.no_summary);
        }

        text_view_overview.setText(overView);
        text_view_rating.setText(movie.getVote_average().toString());

        String release_date = movie.getRelease_date();
        if(release_date == null) {
            text_view_release_date.setTypeface(null, Typeface.ITALIC);
            release_date = getString(R.string.no_release_date);
        }
        text_view_release_date.setText(release_date);
    }
}
