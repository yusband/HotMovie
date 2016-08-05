package com.patrick.android.hotmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ContentFragment())
                    .commit();
        }

    }

//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//    }

//    @Override
//    protected void onStop() {
//       getSupportFragmentManager().popBackStack();
//        super.onStop();
//    }

//    @Override
//    protected void onDestroy() {
//        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("only")).commit();
//        super.onDestroy();
//    }
}