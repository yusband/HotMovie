package com.patrick.android.hotmovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.patrick.android.hotmovie.db.MovieContact.PopularMovieEntry;
/**
 * Created by Administrator on 2016/9/8.
 */
public class OpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    static final String DATABASE_NAME = "movie.db";
    public OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_POPULAR_DETAIL_TABLE = "CREATE TABLE " + PopularMovieEntry.TABLE_NAME + " (" +
                MovieContact.PopularMovieEntry._ID + " INTEGER PRIMARY KEY," +
                MovieContact.PopularMovieEntry.COLUMN_NUMBER+ " TEXT NOT NULL,"+
                PopularMovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                PopularMovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieContact.PopularMovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContact.PopularMovieEntry.COLUMN_COMMENT + " TEXT , " +
                MovieContact.PopularMovieEntry.COLUMN_VOTE + " TEXT NOT NULL, "+
                MovieContact.PopularMovieEntry.COLUMN_PATH + " TEXT NOT NULL, " +
                MovieContact.PopularMovieEntry.COLUMN_TRAILER + " TEXT , " +
                MovieContact.PopularMovieEntry.COLUMN_LENGTH+ " TEXT "+

                " );";
        final String SQL_CREATE_TOP_DETAIL_TABLE = "CREATE TABLE " + MovieContact.TopMovieEntry.TABLE_NAME + " (" +
                MovieContact.TopMovieEntry._ID + " INTEGER PRIMARY KEY," +
                MovieContact.TopMovieEntry.COLUMN_NUMBER+ " TEXT NOT NULL,"+
                MovieContact.TopMovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieContact.TopMovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieContact.TopMovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContact.TopMovieEntry.COLUMN_COMMENT + " TEXT , " +
                MovieContact.TopMovieEntry.COLUMN_VOTE + " TEXT NOT NULL, "+
                MovieContact.TopMovieEntry.COLUMN_PATH + " TEXT NOT NULL, " +
                MovieContact.TopMovieEntry.COLUMN_TRAILER + " TEXT , " +
                MovieContact.TopMovieEntry.COLUMN_LENGTH+ " TEXT "+

                " );";
        final String SQL_CREATE_FAVOR_DETAIL_TABLE = "CREATE TABLE " + MovieContact.FavorMovieEntry.TABLE_NAME + " (" +
                MovieContact.FavorMovieEntry._ID + " INTEGER PRIMARY KEY," +
                MovieContact.FavorMovieEntry.COLUMN_NUMBER+ " TEXT NOT NULL,"+
                MovieContact.FavorMovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieContact.FavorMovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieContact.FavorMovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContact.FavorMovieEntry.COLUMN_COMMENT + " TEXT , " +
                MovieContact.FavorMovieEntry.COLUMN_VOTE + " TEXT NOT NULL, "+
                MovieContact.FavorMovieEntry.COLUMN_PATH + " TEXT NOT NULL, " +
                MovieContact.FavorMovieEntry.COLUMN_TRAILER + " TEXT , " +
                MovieContact.FavorMovieEntry.COLUMN_LENGTH+ " TEXT "+

                " );";
        sqLiteDatabase.execSQL(SQL_CREATE_TOP_DETAIL_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_POPULAR_DETAIL_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FAVOR_DETAIL_TABLE);
        Log.i("db ","is created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PopularMovieEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContact.TopMovieEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContact.FavorMovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
