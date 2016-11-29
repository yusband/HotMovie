package com.patrick.android.hotmovie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.patrick.android.hotmovie.R;

public class DetailActivity extends AppCompatActivity {
    private int position;
    DetailFragment detailFragment;
    private  String address;
    public  static boolean IS_FROM_COLLECT_ACTIVITY=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        detailFragment=new DetailFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, detailFragment)
                    .commit();
            Intent intent = getIntent();
            position = intent.getIntExtra("position", -1);


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("detailfragment","activitySTARTed");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("detailfragment","activitydestoyed");


    }
}