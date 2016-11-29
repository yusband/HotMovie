package com.patrick.android.hotmovie.db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Administrator on 2016/8/4.
 */
public class MovieContact {

    public static final String CONTENT_AUTHORITY = "com.patrick.android.hotmovie";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_TOP_MOVIE = "top_movie";
    public static final String PATH_POPULAR_MOVIE = "popular_movie";
    public static final String PATH_FAVOR_MOVIE = "favor_movie";

    public static  final class TopMovieEntry implements BaseColumns{

    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_TOP_MOVIE).build();
    public static Uri buildMovieUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }


    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd" + CONTENT_AUTHORITY + "/" + PATH_TOP_MOVIE;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd" + CONTENT_AUTHORITY + "/" + PATH_TOP_MOVIE;
    public static final String TABLE_NAME = "top_movie";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_RELEASE_DATE = "date";
    public static final String COLUMN_VOTE = "rate";
        public static final String COLUMN_TRAILER = "trailer";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_LENGTH = "length";
}

    public static  final class PopularMovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POPULAR_MOVIE).build();
        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd" + CONTENT_AUTHORITY + "/" + PATH_POPULAR_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd" + CONTENT_AUTHORITY + "/" + PATH_POPULAR_MOVIE;
        public static final String TABLE_NAME = "popular_movie";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_PATH = "path";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_NUMBER = "number";
        public static final String COLUMN_RELEASE_DATE = "date";
        public static final String COLUMN_VOTE = "rate";
        public static final String COLUMN_TRAILER = "trailer";
        public static final String COLUMN_COMMENT = "comment";
        public static final String COLUMN_LENGTH = "length";
    }
    public static  final class FavorMovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOR_MOVIE).build();
        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd" + CONTENT_AUTHORITY + "/" + PATH_FAVOR_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd" + CONTENT_AUTHORITY + "/" + PATH_FAVOR_MOVIE;
        public static final String TABLE_NAME = "favor_movie";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_PATH = "path";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_NUMBER = "number";
        public static final String COLUMN_RELEASE_DATE = "date";
        public static final String COLUMN_VOTE = "rate";
        public static final String COLUMN_TRAILER = "trailer";
        public static final String COLUMN_COMMENT = "comment";
        public static final String COLUMN_LENGTH = "length";
    }
}
