package com.patrick.android.hotmovie.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
import android.view.View;
import android.view.ViewGroup;

import com.patrick.android.hotmovie.R;
import com.patrick.android.hotmovie.adapter.DetailAdapter;
import com.patrick.android.hotmovie.db.MovieContact;

import static com.patrick.android.hotmovie.ui.ContentFragment.SP_FAVOR_MOVIE;
import static com.patrick.android.hotmovie.ui.ContentFragment.SP_POPULAR_MOVIE;
import static com.patrick.android.hotmovie.ui.ContentFragment.SP_TOP_MOVIE;

/**
 * Created by Administrator on 2016/8/16.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private int position=0;
    private final String TAG = getClass().getSimpleName();
    private Context context=null;
    public static boolean IS_FAVOURED=false;
    private RecyclerView mRecyclerView;
    private DetailAdapter detailAdapter=null;
    private RecyclerView.LayoutManager mLayoutManager;
    private  String current_table;
    private  String current_movie_title;
    private   int current_movie_position;
    private static final String[] POPULAR_MOVIE_COLUMNS = {
            MovieContact.PopularMovieEntry.TABLE_NAME + "." + MovieContact.PopularMovieEntry._ID,
            MovieContact.PopularMovieEntry.COLUMN_TITLE,
            MovieContact.PopularMovieEntry.COLUMN_RELEASE_DATE,
            MovieContact.PopularMovieEntry.COLUMN_VOTE,
            MovieContact.PopularMovieEntry.COLUMN_NUMBER,
            MovieContact.PopularMovieEntry.COLUMN_OVERVIEW,
            MovieContact.PopularMovieEntry.COLUMN_PATH,
            MovieContact.PopularMovieEntry.COLUMN_COMMENT,
            MovieContact.PopularMovieEntry.COLUMN_LENGTH,
            MovieContact.PopularMovieEntry.COLUMN_TRAILER

    };
    private static final String[] TOP_MOVIE_COLUMNS = {
            MovieContact.TopMovieEntry.TABLE_NAME + "." + MovieContact.TopMovieEntry._ID,
            MovieContact.TopMovieEntry.COLUMN_TITLE,
            MovieContact.TopMovieEntry.COLUMN_RELEASE_DATE,
            MovieContact.TopMovieEntry.COLUMN_VOTE,
            MovieContact.TopMovieEntry.COLUMN_NUMBER,
            MovieContact.TopMovieEntry.COLUMN_OVERVIEW,
            MovieContact.TopMovieEntry.COLUMN_PATH,
            MovieContact.TopMovieEntry.COLUMN_COMMENT,
            MovieContact.TopMovieEntry.COLUMN_LENGTH,
            MovieContact.TopMovieEntry.COLUMN_TRAILER

    };
    private static final String[] FAVOUR_MOVIE_COLUMNS = {
            MovieContact.FavorMovieEntry.TABLE_NAME + "." + MovieContact.FavorMovieEntry._ID,
            MovieContact.FavorMovieEntry.COLUMN_TITLE,
            MovieContact.FavorMovieEntry.COLUMN_RELEASE_DATE,
            MovieContact.FavorMovieEntry.COLUMN_VOTE,
            MovieContact.FavorMovieEntry.COLUMN_NUMBER,
            MovieContact.FavorMovieEntry.COLUMN_OVERVIEW,
            MovieContact.FavorMovieEntry.COLUMN_PATH,
            MovieContact.FavorMovieEntry.COLUMN_COMMENT,
            MovieContact.FavorMovieEntry.COLUMN_LENGTH,
            MovieContact.FavorMovieEntry.COLUMN_TRAILER

    };

    private static final int TOP_MOVIE_LOADER = 20;
    private static final int POPULAR_MOVIE_LOADER = 30;
    private static final int FAVOUR_MOVIE_LOADER = 40;
    private static final int FAVOUR_MOVIE_LOADER_SPEC = 41;
    public static final int COL_MOVIE_ID = 0;
    public static final int COL_TITLE = 1;
    public static final int COL_RELEASE_DATE = 2;
    public static final int COL_VOTE = 3;
    public static final int COL_NUMBER = 4;
    public static final int COL_OVERVIEW = 5;
    public static final int COL_PATH = 6;
    public static final int COL_COMMENT = 7;
    public static final int COL_LENGTH = 8;
    public static final int COL_TRAILER = 9;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        LoaderManager loaderManager=getLoaderManager();
        initLoader(POPULAR_MOVIE_LOADER,null,this,loaderManager);
        initLoader(TOP_MOVIE_LOADER,null,this,loaderManager);
        initLoader(FAVOUR_MOVIE_LOADER_SPEC,null,this,loaderManager);
        initLoader(FAVOUR_MOVIE_LOADER,null,this,loaderManager);
        super.onActivityCreated(savedInstanceState);



    }
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();

        Bundle bundle=getArguments();
        if (bundle!=null) {
            Log.i("detailFragment","bundle is not null!");
            current_movie_position = bundle.getInt("position");
            current_movie_title = bundle.getString("title");
            current_table = bundle.getString("table");

        }
            Intent intent=getActivity().getIntent();
        if(intent!=null) {
            current_table = intent.getStringExtra("table");
            current_movie_title = intent.getStringExtra("title");
            current_movie_position = intent.getIntExtra("position", -1);
//            Log.i("detail intent table", current_table);
//            Log.i("detail intent id", current_movie_title);
            Log.i("detail intent position", String.valueOf(current_movie_position));
            Log.i("detailfragment", "oncreate");
        }
        }
    public static <T> void initLoader(final int loaderId, final Bundle args, final LoaderManager.LoaderCallbacks<T> callbacks,
                                      final LoaderManager loaderManager) {
        final Loader<T> loader = loaderManager.getLoader(loaderId);
        if (loader != null && loader.isReset()) {
            Log.i("initloader","1");

            loaderManager.restartLoader(loaderId, args, callbacks);
        } else {
            Log.i("initloader","2");
            loaderManager.initLoader(loaderId, args, callbacks);
        }
    }
    public void onStart() {
        super.onStart();
        Log.i("detailfragment","onstart");




    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("detailfragment", "oncreatView");

        if (context == null) {
            Log.i("detailfragment", "context is nullQ");
        } else {
            Log.i("detailfragment", "context is not nullQ");
        }
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("detailfragment","ondestroy");

    }
    /*
    先前写的回调方法，在平板布局中当左侧的数据加载完毕时再让右侧的Fragment开始加载
     */
    public void LoadContentsFromList(){
        Log.i("detailfragment","loadfrom");
        Bundle argument = getArguments();
        if((argument==null))Log.i("detailfragment","bundle is null");
        if (argument != null) {

            position = argument.getInt("position", 0);
            if (!ContentFragment.getList().isEmpty()){
                String id = ContentFragment.getList().get(position).getId();
                Log.i("fragment", id);


            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        switch (id) {


            case POPULAR_MOVIE_LOADER:

                Log.i("LOG_TAG", "In onCreatepopularLoader");
                Loader<Cursor> loader1 = new CursorLoader(
                        getActivity(), MovieContact.PopularMovieEntry.CONTENT_URI,
                        POPULAR_MOVIE_COLUMNS,
                        null,
                        null,
                        null);
                return loader1;

            case TOP_MOVIE_LOADER:
                Log.i("LOG_TAG", "In onCreatetopLoader");
                Loader<Cursor> loader2 = new CursorLoader(
                        getActivity(), MovieContact.TopMovieEntry.CONTENT_URI,
                        TOP_MOVIE_COLUMNS,
                        null,
                        null,
                        null);
                return loader2;
            case FAVOUR_MOVIE_LOADER_SPEC:
                Log.i("LOG_TAG", "In onCreatefavourspcLoader");
                String title=null;
                if(getArguments()!=null){
                   title = getArguments().getString("title");
                }
                else {
                    title=current_movie_title;
                }
                Log.i("detail_getbundle", "position is" + current_movie_position);
                Log.i("detail_getbundle", "title is " + current_movie_title);
                Log.i("detail_getbundle", "title in argument is " + title);
                Log.i("detail_getbundle", "table is" + current_table);
//                Log.i("t id in loader", current_movie_title);
                Loader<Cursor> loader3 = new CursorLoader(
                        getActivity(), MovieContact.FavorMovieEntry.CONTENT_URI,
                        FAVOUR_MOVIE_COLUMNS,
                        "title=?", new String[]{title}
                        ,
                        null);
                return loader3;
            case FAVOUR_MOVIE_LOADER:

                Log.i("LOG_TAG", "In onCreatefavorrLoader");
                Loader<Cursor> loader4 = new CursorLoader(
                        getActivity(), MovieContact.FavorMovieEntry.CONTENT_URI,
                        FAVOUR_MOVIE_COLUMNS,
                        null,
                        null,
                        null);
                return loader4;

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //current_table只有在竖直屏幕中才会被ContentFragment中的intent传递过来
        Log.i("in detailfragment", "onloadFinished is called");
//        Log.i("onloadFinished", "table is " + current_table);
        String table=null;
        int position=-2;
        //取出从MainActivity的OnReceive或OnItemClickLand中放进去的数据(table/position)
        if(getArguments()!=null) {
            table = getArguments().getString("table", "");
            position = getArguments().getInt("position", -2);

        }
        if (loader.getId() == TOP_MOVIE_LOADER) {
            Log.i("onloadFinished", "top table is " + table);
            if(((TextUtils.equals(table, SP_TOP_MOVIE)))||(TextUtils.equals(current_table, SP_TOP_MOVIE))) {

                if(position>=0){
                    detailAdapter = new DetailAdapter(context, data, position);
                }
                else {

                    detailAdapter = new DetailAdapter(context, data, current_movie_position);
                }
                mRecyclerView.setAdapter(detailAdapter);
                Log.i("in detailfragment", "loader id is " + String.valueOf(loader.getId()));
            }
        }
        if (loader.getId() == POPULAR_MOVIE_LOADER) {
            Log.i("onloadFinished", "pop table is " + table);
            if ((TextUtils.equals(table, SP_POPULAR_MOVIE))||(TextUtils.equals(current_table, SP_POPULAR_MOVIE))) {
                if(position>=0){
                    detailAdapter = new DetailAdapter(context, data, position);
                }
                else {
                    detailAdapter = new DetailAdapter(context, data, current_movie_position);
                }
                mRecyclerView.setAdapter(detailAdapter);
                Log.i("in detailfragment", "loader id is " + String.valueOf(loader.getId()));
            }




        }if (loader.getId() == FAVOUR_MOVIE_LOADER) {
            Log.i("onloadFinished", "favor table is " + table);
            if ((TextUtils.equals(table, SP_FAVOR_MOVIE)) || (TextUtils.equals(current_table, SP_FAVOR_MOVIE))) {
                if (position >= 0) {
                    detailAdapter = new DetailAdapter(context, data, position);
                } else {
                    detailAdapter = new DetailAdapter(context, data, current_movie_position);
                }
                mRecyclerView.setAdapter(detailAdapter);
                Log.i("exist", String.valueOf(data.getCount()));
                Log.i("in detailfragment", "loader id is " + String.valueOf(loader.getId()));
                Log.i("in detailfragment", "loader id is " + String.valueOf(data.getCount()));
            }

        }
        if (loader.getId() == FAVOUR_MOVIE_LOADER_SPEC) {
            Log.i("onloadFinished", "favor tille count  " +data.getCount());

                if (data.getCount() == 0) {
                    IS_FAVOURED = false;
                     } else {
                    IS_FAVOURED = true;
                    }


            }
        }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.i("contentfragment", "loader is reset");
        detailAdapter.getCursorRecyclerViewAdapter().swapCursor(null);    }
}

