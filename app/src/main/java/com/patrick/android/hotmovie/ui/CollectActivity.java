package com.patrick.android.hotmovie.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.patrick.android.hotmovie.R;
import com.patrick.android.hotmovie.adapter.CollectAdapter;
import com.patrick.android.hotmovie.db.MovieContact;
import com.patrick.android.hotmovie.module.Movie;
import com.patrick.android.hotmovie.module.MovieSub;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends AppCompatActivity {



    public static List<Movie> list=new ArrayList<>();
    public static List<MovieSub> listsub=new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        DetailActivity.IS_FROM_COLLECT_ACTIVITY=true;
        mRecyclerView = (RecyclerView )findViewById(R.id.collect_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        File imgFile = new  File("/storage/emulated/0/Ghostbusters.png");
//
//        if(imgFile.exists()){
//            Log.i("imageview","loaded");
//
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//
//            ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);
//
//            myImage.setImageBitmap(myBitmap);
//
//        }
        fillList();
        CollectAdapter adapter=new CollectAdapter(this,list);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("collect","onstart");
//        if((list.size()==0)|(listsub.size()==0))

    }

    private void fillList(){
    Cursor cursor=getContentResolver().query(MovieContact.PopularMovieEntry.CONTENT_URI,null,null,null,null);
    Log.i("cursor", String.valueOf(cursor.getCount()));
    if(cursor!=null){
        int i =0;
        while ((cursor.moveToNext())){
         {
            Movie movie=new Movie();
            movie.setVote_average(cursor.getString(cursor.getColumnIndex("rate")));
            movie.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            movie.setRelease_date(cursor.getString(cursor.getColumnIndex("date")));
            movie.setLocal_path(cursor.getString(cursor.getColumnIndex("path")));
            list.add(movie);


            MovieSub movieSub=new MovieSub();
            movieSub.setContent(cursor.getString(cursor.getColumnIndex("comment")));
            Log.i("collectactivty"+i,cursor.getString(cursor.getColumnIndex("comment")));
            i++;
            listsub.add(movieSub);}

        }
        cursor.close();}
        int size=list.size();
        Log.i("size", String.valueOf(size));
        for(int i=0;i<size;i++){
        Log.i("size", list.get(i).getTitle());
    }
}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        DetailActivity.IS_FROM_COLLECT_ACTIVITY=false;
        Log.i("collect","ondestroy");
        list.clear();
        listsub.clear();
    }
}
