package com.patrick.android.hotmovie.service;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.patrick.android.hotmovie.db.MovieContact;

/**
 * Created by Administrator on 2016/12/14.
 */

public class QueryFirstService extends IntentService {
    public static final String SP_TOP_MOVIE="top_rated";
    public static final String SP_POPULAR_MOVIE="popular";
    public static final String SP_FAVOR_MOVIE="favor";
    public final String TAG=this.getClass().getSimpleName();
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public QueryFirstService(String name) {
        super(name);
    }
    public QueryFirstService() {
        this("");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String table = intent.getStringExtra("table");
        Cursor cursor = null;
        String title = null;

        if (TextUtils.equals(table, SP_FAVOR_MOVIE)) {
                Log.i(TAG,"I am in favor");

                cursor = getContentResolver().query(MovieContact.FavorMovieEntry.buildMovieUri(0), null, null, null, null);

            if( cursor != null && cursor.moveToFirst() ){
                title = cursor.getString(cursor.getColumnIndex(MovieContact.TopMovieEntry.COLUMN_TITLE));
                cursor.close();
            }


             }
        if (TextUtils.equals(table, SP_POPULAR_MOVIE)) {
                Log.i(TAG,"I am in pop");
                cursor = getContentResolver().query(MovieContact.PopularMovieEntry.buildMovieUri(0), null, null, null, null);
            if( cursor != null && cursor.moveToFirst() ){
                title = cursor.getString(cursor.getColumnIndex("title"));
                cursor.close();
            }

             }
        if (TextUtils.equals(table, SP_TOP_MOVIE)) {
                Log.i(TAG,"I am in top");
                 cursor = getContentResolver().query(MovieContact.TopMovieEntry.buildMovieUri(0), null, null, null, null);
            if( cursor != null && cursor.moveToFirst() ){
                title = cursor.getString(cursor.getColumnIndex("title"));
                cursor.close();
            }


             }
                String status = "done";
                Intent localIntent =new Intent(Constants.BROADCAST_ACTION).putExtra(Constants.EXTENDED_DATA_STATUS, status).putExtra("title", title);
                // Broadcasts the Intent to receivers in this app.
                LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
            cursor.close();


    }
    public final class Constants {

        // Defines a custom Intent action
        public static final String BROADCAST_ACTION =
                "com.patrick.android.hotmovie.service.queryfirst.BROADCAST";

        // Defines the key for the status "extra" in an Intent
        public static final String EXTENDED_DATA_STATUS =
                "com.patrick.android.hotmovie.service.queryfirst.STATUS";

    }
}
