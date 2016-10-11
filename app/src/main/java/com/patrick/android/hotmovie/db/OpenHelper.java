package com.patrick.android.hotmovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.patrick.android.hotmovie.db.MovieContact.MovieEntry;
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
        final String SQL_CREATE_DETAIL_TABLE = "CREATE TABLE " +MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY," +
                MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_VOTE + " TEXT NOT NULL "+
                " );";
        sqLiteDatabase.execSQL(SQL_CREATE_DETAIL_TABLE);
        Log.i("db ","is created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
