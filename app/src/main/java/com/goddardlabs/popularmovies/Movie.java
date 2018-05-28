package com.goddardlabs.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    //this is the base of the url
    private final String image_url_base = "https://image.tmdb.org/t/p/w1280/"; //w1280 //w185 //w1000_and_h563_face
    private final String image_url_backdrop_base = "https://image.tmdb.org/t/p/w1000_and_h563_face";

    private String adult;

    public String getBack_drop_path() {
        return image_url_backdrop_base + back_drop_path;
    }

    public void setBack_drop_path(String back_drop_path) {
        this.back_drop_path = back_drop_path;
    }

    private String back_drop_path;

    private String belongs_to_collection;
    private String budget;
    private String genres;
    private String homepage;

    ////////////////////////////////////////
    //required for stage 1
    ///////////////////////////////////////
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public String getPoster_path() {
        return image_url_base + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    private String poster_path;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    private String overview;

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    private String vote_average;

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    private String release_date;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.poster_path);
        parcel.writeString(this.overview);
        parcel.writeString(this.vote_average);
        parcel.writeString(this.release_date);
        parcel.writeString(this.back_drop_path);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie() {

    }

    private Movie(Parcel parcel) {
        this.title = parcel.readString();
        this.poster_path = parcel.readString();
        this.overview = parcel.readString();
        this.vote_average = parcel.readString();
        this.release_date = parcel.readString();
        this.back_drop_path = parcel.readString();
    }
}
