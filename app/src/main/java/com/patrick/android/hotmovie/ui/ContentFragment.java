package com.patrick.android.hotmovie.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.patrick.android.hotmovie.R;
import com.patrick.android.hotmovie.adapter.CursorRecyclerViewAdapter;
import com.patrick.android.hotmovie.adapter.TrueCursorRecyclerViewAdapter;
import com.patrick.android.hotmovie.db.MovieContact;
import com.patrick.android.hotmovie.module.Movie;
import com.patrick.android.hotmovie.net.FetchDataService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/27.
 */
public class ContentFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,SharedPreferences.OnSharedPreferenceChangeListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static Boolean IS_EMPTY=null;
    private Cursor cursor_popular=null;
    private Cursor cursor_top=null;
    String sort_order;
    private String order_before;
    private static final int TOP_MOVIE_LOADER = 1;
    private static final int POPULAR_MOVIE_LOADER = 0;
    public static final String SP_TOP_MOVIE="top_rated";
    public static final String SP_POPULAR_MOVIE="popular";
    public static final String SP_FAVOR_MOVIE="favor";
    private CursorRecyclerViewAdapter cursorRecyclerViewAdapter;
    public static final String TAG = "ContentFragment";
    SharedPreferences sharedPref;
    private static List<Movie> list = new ArrayList();
    private static final String KEY = "rate";
    public static boolean LIST_LOAD_IS_DONE = false;
    private static final String[] POPULAR_MOVIE_COLUMNS = {
                    MovieContact.PopularMovieEntry.TABLE_NAME + "." + MovieContact.PopularMovieEntry._ID,
                    MovieContact.PopularMovieEntry.COLUMN_TITLE,
                    MovieContact.PopularMovieEntry.COLUMN_RELEASE_DATE,
                    MovieContact.PopularMovieEntry.COLUMN_VOTE,
                    MovieContact.PopularMovieEntry.COLUMN_NUMBER,
                    MovieContact.PopularMovieEntry.COLUMN_OVERVIEW,
                    MovieContact.PopularMovieEntry.COLUMN_PATH,


                    };
    private static final String[] TOP_MOVIE_COLUMNS = {
            MovieContact.TopMovieEntry.TABLE_NAME + "." + MovieContact.TopMovieEntry._ID,
            MovieContact.TopMovieEntry.COLUMN_TITLE,
            MovieContact.TopMovieEntry.COLUMN_RELEASE_DATE,
            MovieContact.TopMovieEntry.COLUMN_VOTE,
            MovieContact.TopMovieEntry.COLUMN_NUMBER,
            MovieContact.TopMovieEntry.COLUMN_OVERVIEW,
            MovieContact.TopMovieEntry.COLUMN_PATH,



    };


    static final int COL_MOVIE_ID = 0;
    static final int COL_TITLE = 1;
    static final int COL_RELEASE_DATE= 2;
    static final int COL_VOTE = 3;
    static final int COL_NUMBER = 4;
    static final int COL_OVERVIEW= 5;
    static final int COL_PATH = 6;


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        String order =sharedPreferences.getString(KEY,"top_rated");
        switch (order){
            case SP_TOP_MOVIE:{
                Log.i("contentframent","0" );
               if(cursor_top!=null)cursorRecyclerViewAdapter.swapCursor(cursor_top);
                TrueCursorRecyclerViewAdapter trueCursorRecyclerViewAdapter=new TrueCursorRecyclerViewAdapter(cursorRecyclerViewAdapter,getActivity(),TrueCursorRecyclerViewAdapter.FRAGMENT_COTENT);
                mRecyclerView.setAdapter(trueCursorRecyclerViewAdapter);
            break;}
            case SP_POPULAR_MOVIE:{
                Log.i("contentframent","1" );
                if(cursor_popular!=null)cursorRecyclerViewAdapter.swapCursor(cursor_popular);
                TrueCursorRecyclerViewAdapter trueCursorRecyclerViewAdapter=new TrueCursorRecyclerViewAdapter(cursorRecyclerViewAdapter,getActivity(),TrueCursorRecyclerViewAdapter.FRAGMENT_COTENT);
                mRecyclerView.setAdapter(trueCursorRecyclerViewAdapter);
                break;
            }
            case SP_FAVOR_MOVIE:{

                Log.i("contentframent","2" );
                break;
            }
        }

    }

    public interface OnItemClickedLandListener {
        public void onItemClickedLand(int position);

        public void onLoadFinished();
    }

    ;
    OnItemClickedLandListener itemClickedLandListener;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LoaderManager loaderManager=getLoaderManager();
        loaderManager.initLoader(POPULAR_MOVIE_LOADER,null,this);
        loaderManager.initLoader(TOP_MOVIE_LOADER,null,this);

        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            itemClickedLandListener = (OnItemClickedLandListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnHeadlineSelectedListener");

        }
    }
    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        switch (id) {

            case POPULAR_MOVIE_LOADER:
                Log.v("LOG_TAG", "In onCreatepopularLoader");
                return new CursorLoader(
                        getActivity(), MovieContact.PopularMovieEntry.CONTENT_URI,
                        POPULAR_MOVIE_COLUMNS,
                        null,
                        null,
                        null);

            case TOP_MOVIE_LOADER:
                Log.v("LOG_TAG", "In onCreatetopLoader");
                return new CursorLoader(
                        getActivity(), MovieContact.TopMovieEntry.CONTENT_URI,
                        TOP_MOVIE_COLUMNS,
                        null,
                        null,
                        null);


        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if(loader.getId()==TOP_MOVIE_LOADER) {
            cursor_top = data;
            if (TextUtils.equals(sharedPref.getString(KEY, ""), SP_TOP_MOVIE)) {
                cursorRecyclerViewAdapter.swapCursor(data);
                Log.i("in contentfragment", "loader id is " + String.valueOf(loader.getId()));
            }
        }
        if(loader.getId()==POPULAR_MOVIE_LOADER){
            cursor_popular=data;
            if(TextUtils.equals(sharedPref.getString(KEY,""),SP_POPULAR_MOVIE))
            cursorRecyclerViewAdapter.swapCursor(data);

            Log.i("in contentfragment", "loader id is "+String.valueOf(loader.getId()));
        }
        }




    @Override
    public void onLoaderReset(Loader loader) {
cursorRecyclerViewAdapter.swapCursor(null);
    }
    public static List<Movie> getList() {
        return list;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragmet_content, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_fragment_content_item_rate:
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(KEY, SP_TOP_MOVIE);
                editor.apply();
                Log.i("sp", sharedPref.getString("KEY", "11"));
                return true;
            case R.id.menu_fragment_content_item_popularity:
                editor = sharedPref.edit();
                editor.putString(KEY, SP_POPULAR_MOVIE);
                editor.apply();
                Log.i("sp", sharedPref.getString("KEY", "12"));
                return true;

//            case R.id.menu_fragment_content_item_collect:
            case R.id.menu_fragment_content_item_collect:
                editor = sharedPref.edit();
                editor.putString(KEY, "favor");
                editor.apply();
                Log.i("sp", sharedPref.getString("KEY", SP_FAVOR_MOVIE));
                return true;

            default:
                return true;
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cursorRecyclerViewAdapter=new CursorRecyclerViewAdapter(getActivity(),null,0);
        TrueCursorRecyclerViewAdapter trueCursorRecyclerViewAdapter=new TrueCursorRecyclerViewAdapter(cursorRecyclerViewAdapter,getActivity(),TrueCursorRecyclerViewAdapter.FRAGMENT_COTENT);
        View rootView = inflater.inflate(R.layout.test_recyclerview, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.test_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(trueCursorRecyclerViewAdapter);

        setHasOptionsMenu(true);

        return rootView;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            list = savedInstanceState.getParcelableArrayList("movie");
            Log.i("contentfragment", "parcelable is loaded");
        }
        Cursor cursor=getActivity().getContentResolver().query(MovieContact.PopularMovieEntry.CONTENT_URI,new String[]{"title"},null,null,null);
        if((cursor!=null)&&cursor.getCount()==0){
        Intent intent=new Intent(getActivity(), FetchDataService.class);
        getActivity().startService(intent);
            Log.i("contentfragment","service is started");
        }
           cursor.close();
        setHasOptionsMenu(true);
        Context context = getActivity();
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        sharedPref.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onStart() {
        super.onStart();


        sort_order = sharedPref.getString(KEY, "popular");
        Log.i(TAG, "now" + sort_order);
        if (list.isEmpty()) {
            Log.i(TAG, "onStart: 1");

        } else if ((sort_order.equals(order_before))) {
            Log.i(TAG, "onStart: 2");


        }

        else {
            list.clear();
            Log.i(TAG, "onStart: 3");

        }
        order_before = sort_order;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movie", (ArrayList<? extends Parcelable>) list);
    }

public void isEmpty(){
    Thread thread= new Thread(new Runnable() {
        @Override
        public void run() {
            Cursor cursor=getActivity().getContentResolver().query(MovieContact.PopularMovieEntry.CONTENT_URI,new String[]{"title"},null,null,null);
            if ((cursor!=null)&&cursor.getCount()>0)IS_EMPTY=false;
            else IS_EMPTY=true;
        }
    }) ;thread.start();
    }


}


