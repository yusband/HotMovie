package com.patrick.android.hotmovie.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/17.
 */

public class MoreDataService extends IntentService{
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private OkHttpClient client = new OkHttpClient();
    public MoreDataService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//               fetchMoreData();
    }
    public String fetchMoreData(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.i("service", url);
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


}
