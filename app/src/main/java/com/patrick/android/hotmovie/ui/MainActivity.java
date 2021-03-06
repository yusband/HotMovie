package com.patrick.android.hotmovie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.patrick.android.hotmovie.R;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements ContentFragment.OnItemClickedLandListener{
    private static  final  String KEY="sort_order";

    int postion_land;
    private final String DETAIL_FRAGMENT_TAG="detail";
private boolean mTwoPane;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!isOnline()){
            Toast.makeText(this,"no net connection",Toast.LENGTH_LONG).show();

        }
        if ((findViewById(R.id.detail_container)!=null)) {
            Log.i("MainActivity","1");
            mTwoPane = true;
            if (savedInstanceState == null) {
                Log.i("try", "..");
                Bundle arguments=new Bundle();
                arguments.putInt("position",0);
                DetailFragment detailFragment=new DetailFragment();
                detailFragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction().add(R.id.detail_container, detailFragment, DETAIL_FRAGMENT_TAG).commit();
            }
        }
        else {
            Log.i("MainActivity","2");

            mTwoPane = false;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity","destroyed");
    }
    public boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
    @Override
    public void onItemClickedLand(int position) {
    this.postion_land=position;
        if (mTwoPane){
            Log.i("MainActivity","1");
            Log.i("MainActivityposition", String.valueOf(position));
            Bundle arguments=new Bundle();
            arguments.putInt("position",position);
            DetailFragment detailFragment=new DetailFragment();
            detailFragment.setArguments(arguments);
            detailFragment.LoadContentsFromList();
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container,detailFragment,DETAIL_FRAGMENT_TAG).commit();
        }
        else {
            Log.i("MainActivity","2");
            Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("identity", ContentFragment.TAG);
                Toast.makeText(this, "CLICKED", Toast.LENGTH_SHORT).show();
                startActivity(intent);}
        }

    @Override
    public void onLoadFinished() {
        DetailFragment detailFragment=(DetailFragment)getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG);
        if(detailFragment!=null)detailFragment.LoadContentsFromList();
    }

    }

