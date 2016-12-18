package com.patrick.android.hotmovie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.patrick.android.hotmovie.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final SplashActivity splashActivity=this;


        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                splashActivity.finish();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };
        Timer timer=new Timer();
        timer.schedule(timerTask,4000);


    }
}
