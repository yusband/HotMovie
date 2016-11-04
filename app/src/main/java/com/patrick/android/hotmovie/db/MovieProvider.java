package com.patrick.android.hotmovie.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2016/9/8.
 */
public class MovieProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    public static final int MOVIE =100 ;
    public static final int MOVIE_ALL =300 ;
    public static final int MOVIE_CERTAIN =400 ;
    private OpenHelper mOpenHelper;
    public static UriMatcher buildUriMatcher() {

        UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
        final String authority=MovieContact.CONTENT_AUTHORITY;
        matcher.addURI(authority,MovieContact.PATH_MOVIE, MOVIE);
        matcher.addURI(authority,MovieContact.PATH_MOVIE+"/#",MOVIE_CERTAIN);
        matcher.addURI(authority,MovieContact.PATH_MOVIE+"/*",MOVIE_ALL);



        // 3) Return the new matcher!
        return matcher;
    }
    @Override
    public boolean onCreate() {
        mOpenHelper=new OpenHelper(getContext());
        return  true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Cursor cursor=null;
        switch (match) {
            case MOVIE: {
                cursor=db.query("movie",strings,s,strings1,null,null,s1);
                 break;
                }
                default:
                    throw new android.database.SQLException("Failed to insert row into " + uri);

            }
        return  cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri){
    final int match = sUriMatcher.match(uri);

    switch (match) {

        case MOVIE_ALL:
            return MovieContact.MovieEntry.CONTENT_TYPE;

        case MOVIE:
            return MovieContact.MovieEntry.CONTENT_ITEM_TYPE;
        case MOVIE_CERTAIN:
            return MovieContact.MovieEntry.CONTENT_ITEM_TYPE;
        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
    }


    }

    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIE: {

                long _id = db.insert(MovieContact.MovieEntry.TABLE_NAME, null, values);
                if ( _id > 0 ){
                    returnUri = MovieContact.MovieEntry.buildMovieUri(_id);
                Log.i("insert", String.valueOf(uri));
                }
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
//            case MOVIE_ALL: {
//
//                long _id = db.insert(MovieContact.MovieEntry.TABLE_NAME, null, values);
//                if ( _id > 0 )
//                    returnUri = MovieContact.MovieEntry.buildWeatherUri(_id);
//                else
//                    throw new android.database.SQLException("Failed to insert row into " + uri);
//                break;
//            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    public int delete(Uri uri, String s, String[] strings) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int deleteRows=0;

        switch (match) {
            case MOVIE_CERTAIN: {

               deleteRows=db.delete(MovieContact.MovieEntry.TABLE_NAME,s,strings);
                if ( deleteRows<= 0 )
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
//            case MOVIE_ALL: {
//
//                Log.i("movie_all","");
//            }
            case  MOVIE:{
                Log.i("movie","");
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteRows;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
