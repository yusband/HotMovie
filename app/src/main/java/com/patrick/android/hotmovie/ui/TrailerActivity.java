package com.patrick.android.hotmovie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.patrick.android.hotmovie.R;
import com.patrick.android.hotmovie.adapter.TrailerAdapter;

import java.util.List;

public class TrailerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    public static List<String> trailerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.trailer_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Intent intent=getIntent();
//        trailerList=intent.getStringArrayListExtra("address");

        TrailerAdapter adapter=new TrailerAdapter(this);
        mRecyclerView.setAdapter(adapter);

//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?"+list.get(0))));
//        Log.i("Video", "Video Playing....");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
