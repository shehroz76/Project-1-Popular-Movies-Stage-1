package com.shehroz.msk.p1_popular_movies.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DELL on 8/11/2016.
 */
public class Movie implements Parcelable {


    int    id;
    String title;
    String original_title;
    String overview;
    String release_date;
    String poster_path;
    String vote_average;

    public Movie(){
    }

    private Movie(Parcel parcel){
        this.id = parcel.readInt();
        this.title = parcel.readString();
        this.original_title = parcel.readString();
        this.poster_path = parcel.readString();
        this.overview = parcel.readString();
        this.vote_average = parcel.readString();
        this.release_date = parcel.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.original_title);
        dest.writeString(this.poster_path);
        dest.writeString(this.overview);
        dest.writeString(this.vote_average);
        dest.writeString(this.release_date);

    }

}


