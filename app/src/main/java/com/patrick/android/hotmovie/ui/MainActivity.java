package com.patrick.android.hotmovie.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.patrick.android.hotmovie.R;
import com.patrick.android.hotmovie.adapter.ContentAdapter;
import com.patrick.android.hotmovie.net.FetchDataService;
import com.patrick.android.hotmovie.service.QueryFirstService;

public class MainActivity extends AppCompatActivity implements ContentAdapter.OnItemClickedLandListener,ContentFragment.OnDetailSwapListener{
public static  final  String KEY="rate";
    public static final String INTENT_RENEW="current_order";

    int postion_land;
    private final String DETAIL_FRAGMENT_TAG="detail";
private boolean mTwoPane;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.action_renew:
                String current_order=getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE).getString("rate","");

                Intent intent=new Intent(this, FetchDataService.class);
                intent.putExtra(INTENT_RENEW,current_order);
                startService(intent);
                break;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // The filter's action is BROADCAST_ACTION
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        IntentFilter mStatusIntentFilter = new IntentFilter(QueryFirstService.Constants.BROADCAST_ACTION);
        ResponseReceiver responseReceiver=new ResponseReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(responseReceiver,mStatusIntentFilter);
        if ((findViewById(R.id.detail_container)!=null)) {
            Log.i("MainActivity","1");
            mTwoPane = true;


                swapDetail();


        }
        else {


            mTwoPane = false;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity","destroyed");
    }

    @Override
    public void onItemClickedLand(int position, String table, String title) {
        if (mTwoPane)

        {
            Log.i("MainActivity_click","position is"+  position);
            Log.i("MainActivity_click","title is "+title);
            Log.i("MainActivity_click","table is"+ table);
            Bundle arguments=new Bundle();
            arguments.putInt("position",position);
            arguments.putString("table",table);
            arguments.putString("title",title);
            DetailFragment detailFragment=new DetailFragment();
            detailFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container,detailFragment,DETAIL_FRAGMENT_TAG).commit();
        }
        else {
                   Log.i("Main_detail_normal","");
                    Intent intent = new Intent(this, DetailActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("position", position);
                    intent.putExtra("table",table);
                    startActivity(intent);

        }
    }

    @Override
    public void onSwapDetail() {
        if(mTwoPane)swapDetail();
    }

    private class ResponseReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            String received=intent.getStringExtra(QueryFirstService.Constants.EXTENDED_DATA_STATUS);
            String title=intent.getStringExtra("title");
            Log.i("receive", received);
            if ((findViewById(R.id.detail_container)!=null)) {
                Log.i("MainActivity","1");
                Bundle arguments=new Bundle();
                arguments.putInt("position",0);
                arguments.putString("table",getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE).getString(MainActivity.KEY,""));
                arguments.putString("title",title);
                DetailFragment detailFragment=new DetailFragment();
                detailFragment.setArguments(arguments);
//                getSupportFragmentManager().popBackStack();
                try{
                 getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, detailFragment, DETAIL_FRAGMENT_TAG).commitAllowingStateLoss();
                }
            catch (IllegalStateException e){
                e.printStackTrace();
            }
            }

        }
    }
    public void swapDetail(){
        if(mTwoPane){
            Intent intent =new Intent(this,QueryFirstService.class);
            intent.putExtra("table",getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE).getString(MainActivity.KEY,""));
            startService(intent);
        }

    }


    }

