package com.goddardlabs.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.goddardlabs.popularmovies.data.Preferences;

public class MainActivity extends AppCompatActivity implements OnTaskCompleted {
    private final String TAG = MainActivity.class.getSimpleName();
    private GridView grid_view;
    private Preferences preferences = new Preferences();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getMovies() {
        if (isNetworkAvailable()) {
            MoviesAsyncTask movieTask = new MoviesAsyncTask(this, getString(R.string.api_key));
            movieTask.execute(preferences.getSortType(this));
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        getMovies();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid_view = findViewById(R.id.gridview);
        grid_view.setOnItemClickListener(moviePosterClickListener);

        if (savedInstanceState == null) {
            getMovies();
        } else {
            Parcelable[] parcelable = savedInstanceState.getParcelableArray(getString(R.string.movie_type_key));

            if (parcelable != null) {
                int numMovieObjects = parcelable.length;
                Movie[] movies = new Movie[numMovieObjects];

                for (int i = 0; i < numMovieObjects; i++) {
                    movies[i] = (Movie) parcelable[i];
                }

                grid_view.setAdapter(new ImageAdapter(this, movies));
            }
        }
    }

    private final GridView.OnItemClickListener moviePosterClickListener = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Movie movie = (Movie) parent.getItemAtPosition(position);

            Intent intent = new Intent(getApplicationContext(), MoviesDetailsActivity.class);
            intent.putExtra(getString(R.string.movie_type_key), movie);

            startActivity(intent);
        }
    };

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onTaskCompleted(Movie[] movies) {
        grid_view.setAdapter(new ImageAdapter(getApplicationContext(), movies));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        int numMovieObjects = grid_view.getCount();
        if (numMovieObjects > 0) {
            Movie[] movies = new Movie[numMovieObjects];
            for (int i = 0; i < numMovieObjects; i++) {
                movies[i] = (Movie) grid_view.getItemAtPosition(i);
            }

            outState.putParcelableArray(getString(R.string.movie_type_key), movies);
        }

        super.onSaveInstanceState(outState);
    }
}
