package com.patrick.android.hotmovie.module;

/**
 * Created by Administrator on 2016/7/27.
 */
public class Movie {
    private String overview;
    private String title;
    private String id;
    private String release_date;
    private String poster_path;
    private String vote_average;
    private String popularity;

    public String getLocal_path() {
        return local_path;
    }

    public void setLocal_path(String local_path) {
        this.local_path = local_path;
    }

    private String local_path;

    public Boolean getCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    private Boolean collected;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


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



}
