package com.goddardlabs.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

interface OnTaskCompleted {
    void onTaskCompleted(Movie[] movies);
}

public class MoviesAsyncTask extends AsyncTask<String, Void, Movie[]> {
    private final String LOG = MoviesAsyncTask.class.getSimpleName();
    private final String api_key;
    private final OnTaskCompleted listener;

    public MoviesAsyncTask(OnTaskCompleted listener, String key) {
        super();

        this.api_key = key;
        this.listener = listener;
    }

    @Override
    protected Movie[] doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            URL url = getApiUrl(params);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            if (builder.length() == 0) {
                return null;
            }

            try {
                return getMoviesJson(builder.toString());
            } catch (JSONException e) {
                Log.e(LOG, e.getMessage(), e);
                e.printStackTrace();
            }

        } catch (IOException e) {
            Log.e(LOG, "Error ", e);
            return null;
        }

        return null;
    }

    private Movie[] getMoviesJson(String moviesJsonStr) throws JSONException {
        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray resultsArray = moviesJson.getJSONArray("results");

        Movie[] movies = new Movie[resultsArray.length()];
        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject movieInfo = resultsArray.getJSONObject(i);

            movies[i] = new Movie();
            movies[i].setTitle(movieInfo.getString("title"));
            movies[i].setPoster_path(movieInfo.getString("poster_path"));
            movies[i].setOverview(movieInfo.getString("overview"));
            movies[i].setVote_average(movieInfo.getString("vote_average"));
            movies[i].setRelease_date(movieInfo.getString("release_date"));
            movies[i].setBack_drop_path(movieInfo.getString("backdrop_path"));
        }

        return movies;
    }

    private URL getApiUrl(String[] parameters) throws MalformedURLException {
        Uri base = Uri.parse("https://api.themoviedb.org/3/discover/movie?");
        Uri.Builder buildUpon = base.buildUpon();
        buildUpon.appendQueryParameter("api_key", this.api_key);
        buildUpon.appendQueryParameter("sort_by", parameters[0]);
        Uri returnUri = buildUpon.build();

        return new URL(returnUri.toString());
    }

    @Override
    protected void onPostExecute(Movie[] movies) {
        super.onPostExecute(movies);

        this.listener.onTaskCompleted(movies);
    }
}
