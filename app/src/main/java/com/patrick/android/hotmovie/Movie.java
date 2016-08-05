package com.patrick.android.hotmovie;

/**
 * Created by Administrator on 2016/7/27.
 */
public class Movie {
    private String overview;
    private String title;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_Path() {
        return poster_path;
    }

    public void setPoster_Path(String path) {
        this.poster_path = path;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    private String release_date;

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String poster_path;
    private String vote_average;
    private String popularity;

}
