package com.patrick.android.hotmovie;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.patrick.android.hotmovie.db.MovieContact;
import com.patrick.android.hotmovie.db.MovieProvider;

/**
 * Created by Administrator on 2016/10/11.
 */

public class UriMatcherTest extends AndroidTestCase {
    private static final long ID=20;
    private static final Uri TEST_MOVIE = MovieContact.PopularMovieEntry.CONTENT_URI;
    private static final Uri TEST_MOVIE_CERTAIN =  MovieContact.PopularMovieEntry.buildMovieUri(ID);

    public void testUriMatcher() {
        UriMatcher testMatcher = MovieProvider.buildUriMatcher();

//        assertEquals("Error: The MOVIE URI was matched incorrectly.", MovieProvider.MOVIE, testMatcher.match(TEST_MOVIE));
//        assertEquals("Error: The MOVIE URI was matched incorrectly.", MovieProvider.MOVIE_CERTAIN, testMatcher.match(TEST_MOVIE_CERTAIN));

    }
}