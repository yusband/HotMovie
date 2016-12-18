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
    public static final int MOVIE_POPULAR =100 ;
    public static final int MOVIE_POPULAR_CERTAIN = 101;
    public static final int MOVIE_TOP = 200;
    public static final int MOVIE_TOP_CERTAIN = 201;
    public static final int MOVIE_FAVOR = 300;
    public static final int MOVIE_FAVOR_CERTAIN = 301;


    private OpenHelper mOpenHelper;

    public static UriMatcher buildUriMatcher() {

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContact.CONTENT_AUTHORITY;
        matcher.addURI(authority, MovieContact.PATH_POPULAR_MOVIE, MOVIE_POPULAR);
        matcher.addURI(authority, MovieContact.PATH_POPULAR_MOVIE +"/#", MOVIE_POPULAR_CERTAIN);
        matcher.addURI(authority, MovieContact.PATH_TOP_MOVIE, MOVIE_TOP);
        matcher.addURI(authority, MovieContact.PATH_TOP_MOVIE +"/#", MOVIE_TOP_CERTAIN);
        matcher.addURI(authority, MovieContact.PATH_FAVOR_MOVIE, MOVIE_FAVOR);
        matcher.addURI(authority, MovieContact.PATH_FAVOR_MOVIE +"/#", MOVIE_FAVOR_CERTAIN);


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
            case MOVIE_POPULAR: {
                cursor=db.query("popular_movie",strings,s,strings1,null,null,s1);
                 break;
                }
            case MOVIE_POPULAR_CERTAIN: {
                cursor=db.query("popular_movie",strings,s,strings1,null,null,s1);
                break;
            }
            case MOVIE_TOP:
            {
                cursor=db.query("top_movie",strings,s,strings1,null,null,s1);
                break;
            }
            case MOVIE_TOP_CERTAIN:
            {
                cursor=db.query("top_movie",strings,s,strings1,null,null,s1);
                break;
            }
            case MOVIE_FAVOR: {
                cursor=db.query("favor_movie",strings,s,strings1,null,null,s1);
                break;
            }
            case MOVIE_FAVOR_CERTAIN: {
                cursor=db.query("favor_movie",strings,s,strings1,null,null,s1);
                break;
            }
                default:
                    throw new android.database.SQLException("unknow uri " + uri);

            }
        return  cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri){
    final int match = sUriMatcher.match(uri);

    switch (match) {
        case MOVIE_TOP:
            return MovieContact.TopMovieEntry.CONTENT_TYPE;
        case MOVIE_POPULAR:
            return MovieContact.PopularMovieEntry.CONTENT_TYPE;
        case MOVIE_TOP_CERTAIN:
            return MovieContact.TopMovieEntry.CONTENT_ITEM_TYPE;
        case MOVIE_POPULAR_CERTAIN:
            return MovieContact.PopularMovieEntry.CONTENT_ITEM_TYPE;
        case MOVIE_FAVOR:
            return MovieContact.FavorMovieEntry.CONTENT_TYPE;
        case MOVIE_FAVOR_CERTAIN:
            return MovieContact.FavorMovieEntry.CONTENT_ITEM_TYPE;
        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
    }


    }

    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIE_POPULAR: {

                long _id = db.insert(MovieContact.PopularMovieEntry.TABLE_NAME, null, values);
                if ( _id > 0 ){
                    returnUri = MovieContact.PopularMovieEntry.buildMovieUri(_id);
                Log.i("insert", String.valueOf(uri));
                }
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case MOVIE_TOP: {

                long _id = db.insert(MovieContact.TopMovieEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContact.TopMovieEntry.buildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
             case MOVIE_FAVOR: {

                long _id = db.insert(MovieContact.FavorMovieEntry.TABLE_NAME, null, values);
                if ( _id > 0 ){
                    returnUri = MovieContact.FavorMovieEntry.buildMovieUri(_id);
                    Log.i("insert", String.valueOf(uri));
                }
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
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
            case  MOVIE_FAVOR_CERTAIN: {

               deleteRows=db.delete(MovieContact.FavorMovieEntry.TABLE_NAME,s,strings);
                if ( deleteRows<= 0 )
                    throw new android.database.SQLException("Failed to delete row " + uri);
                break;
            }
            case  MOVIE_FAVOR: {

                deleteRows=db.delete(MovieContact.FavorMovieEntry.TABLE_NAME,s,strings);
                Log.i("provider", String.valueOf(deleteRows));
                if ( deleteRows<= 0 )
                    throw new android.database.SQLException("Failed to delete row " + uri);
                break;
            }

            case MOVIE_POPULAR:{

                deleteRows=db.delete(MovieContact.PopularMovieEntry.TABLE_NAME,"1",strings);
                Log.i("provider", String.valueOf(deleteRows));
                if ( deleteRows<= 0 )
                    throw new android.database.SQLException("Failed to delete table popular" + uri);
                break;
            }
            case MOVIE_TOP:{

                deleteRows=db.delete(MovieContact.TopMovieEntry.TABLE_NAME,"1",strings);
                Log.i("provider", String.valueOf(deleteRows));
                if ( deleteRows<= 0 )
                    throw new android.database.SQLException("Failed to delete table top" + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteRows;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        int rows_update;
        Log.i("cp_update",s);
        Log.i("cp_update", String.valueOf(strings));
        switch (match) {
            case MOVIE_POPULAR:
                Log.i("cp_update",s);
                Log.i("cp_update", strings[0]);
                rows_update = db.update(MovieContact.PopularMovieEntry.TABLE_NAME, contentValues ,s,strings);

                break;

            case MOVIE_TOP:

                rows_update= db.update(MovieContact.TopMovieEntry.TABLE_NAME,contentValues ,s,strings);

                break;

            case MOVIE_FAVOR:

                rows_update =db.update(MovieContact.TopMovieEntry.TABLE_NAME,contentValues ,s,strings);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rows_update != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows_update;
    }
}
