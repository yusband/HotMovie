package com.patrick.android.hotmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private int position;
    DetailFragment detailFragment;
    private  String address;

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
    protected void onDestroy() {
        super.onDestroy();


    }
}