package com.patrick.android.hotmovie;

import android.net.Uri;
import android.test.AndroidTestCase;

import com.patrick.android.hotmovie.db.MovieContact;
import com.patrick.android.hotmovie.db.MovieProvider;

/**
 * Created by Administrator on 2016/12/7.
 */

public class DeleteTest extends AndroidTestCase {
    private static final int ROW=1;
    private static  final Uri POPULAR_TABLE= MovieContact.PopularMovieEntry.CONTENT_URI;
    public void testDelete() {
        MovieProvider movieProvider=new MovieProvider();
        movieProvider.onCreate();
        assertEquals("Error: The MOVIE URI was matched incorrectly.", ROW,movieProvider.delete(POPULAR_TABLE,null,null));


    }
}
