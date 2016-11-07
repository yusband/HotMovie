package com.patrick.android.hotmovie.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.patrick.android.hotmovie.R;
import com.patrick.android.hotmovie.adapter.MyAdapter;
import com.patrick.android.hotmovie.module.Movie;
import com.patrick.android.hotmovie.module.MovieSub;
import com.patrick.android.hotmovie.module.MovieTrailer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/16.
 */
public class DetailFragment extends Fragment {
    private int position=0;
    private final String TAG = getClass().getSimpleName();
    private String address;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private  int count=0;
    public static List<MovieSub> list_comment = new ArrayList<>();
    public static List<MovieTrailer> list_trailer = new ArrayList<>();
    public static List<Movie> list_movive=new ArrayList<>();

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("detailfragment","oncreate");

        }

    public void onStart() {
        super.onStart();
        Log.i("detailfragment","onstart");
        Intent intent = getActivity().getIntent();
        if ((intent.getStringExtra("identity") != null) && intent.getStringExtra("identity").equals("ContentFragment"))
        {     Log.i("detailfragment","oncreate1");
            list_movive=ContentFragment.getList();
            position = intent.getIntExtra("position", -1);
            //根据点击位置，在Detail中取出相应数值，取出ContentFragment中list<Movie>中对应位置的电影id
            String ID = ContentFragment.getList().get(position).getId();
            GetCommentTask getCommentTask = new GetCommentTask();
            getCommentTask.execute(ID);
            GetTrailerTask getTrailerTask = new GetTrailerTask();
            getTrailerTask.execute(ID);
        }
        if ((intent.getStringExtra("identity") != null) && intent.getStringExtra("identity").equals("CollectActivity")){
            Log.i("detailfragment","oncreate2");
            Log.i("detailfragment", String.valueOf(list_movive.size()));
            Log.i("detailfragment",String.valueOf(list_comment.size()));
            list_comment= CollectActivity.listsub;
            list_movive=CollectActivity.list;
            Log.i("detailfragment", String.valueOf(list_movive.size()));
            Log.i("detailfragment",String.valueOf(list_comment.size()));
            position=intent.getIntExtra("position",0);
            MyAdapter myAdapter = new MyAdapter(getActivity(), list_movive, list_comment, position);
            mRecyclerView.setAdapter(myAdapter);
        }
            //自定义的adapter会传入两个列表，分别是电影详细信息的对象列表和电影评论的对象列表；
            //position是intent中储存的关于电影在gridview中的位置--对应电影在电影详细信息的对象列表中的位置


    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("detailfragment","oncreatVIEW");
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        return rootView;
    }

    public class GetCommentTask extends AsyncTask<String, Void, List<MovieSub>> {


        @Override
        protected List<MovieSub> doInBackground(String... params) {
            if (params.length != 0) {
                String id = params[0];
                final String ADDRESS = "http://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=6240369edd9386c3d88daa7510b4b921";
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                String dataOutput = null;
//            Uri.Builder builder=Uri.Builder.
                try {
                    URL url = new URL(ADDRESS);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    StringBuffer stringBuffer = new StringBuffer();
                    if (inputStream == null) {
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuffer.append(line + "\n");
                    }
                    if (stringBuffer.length() == 0) {
                    }
                    dataOutput = stringBuffer.toString();

                    Log.i(TAG, dataOutput);
                    JsonParser jsonParser = new JsonParser();
                    JsonElement jelement = jsonParser.parse(dataOutput);
                    JsonObject object = jelement.getAsJsonObject();
                    JsonArray array = object.getAsJsonArray("results");
                    for (int j = 0; j < array.size(); j++) {
                        Gson gson = new Gson();
//                     List<MovieSub> moviesList=gson.fromJson(array.get(j).getAsJsonObject(), new TypeToken<List<MovieSub>>(){}.getType());
                        MovieSub moviesub = gson.fromJson(array.get(j).getAsJsonObject(), MovieSub.class);
                        list_comment.add(moviesub);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }

                }
            }
            if (list_comment != null) {
                return list_comment;
            } else {
                Log.i(TAG, "list_comment is null");
                return null;

            }
        }

        @Override
        protected void onPostExecute(List<MovieSub> content) {
            list_comment = content;
            MyAdapter myAdapter = new MyAdapter(getActivity(), ContentFragment.getList(), list_comment, position);
            mRecyclerView.setAdapter(myAdapter);
        }
    }

    public class GetTrailerTask extends AsyncTask<String, Void, List<MovieTrailer>> {

        @Override
        protected List<MovieTrailer> doInBackground(String... strings) {
            if (strings.length != 0) {
                String id = strings[0];
                final String ADDRESS = "http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=6240369edd9386c3d88daa7510b4b921";
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                String dataOutput = null;
                try {
                    URL url = new URL(ADDRESS);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    StringBuffer stringBuffer = new StringBuffer();
                    if (inputStream == null) {
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuffer.append(line + "\n");
                    }
                    if (stringBuffer.length() == 0) {
                    }
                    dataOutput = stringBuffer.toString();
                    Log.i(TAG, dataOutput);
                    JsonParser jsonParser = new JsonParser();
                    JsonElement jelement = jsonParser.parse(dataOutput);
                    JsonObject object = jelement.getAsJsonObject();
                    JsonArray array = object.getAsJsonArray("results");
                    for (int j = 0; j < array.size(); j++) {
                        Gson gson = new Gson();
//                     List<MovieSub> moviesList=gson.fromJson(array.get(j).getAsJsonObject(), new TypeToken<List<MovieSub>>(){}.getType());
                        MovieTrailer movieTrailer = gson.fromJson(array.get(j).getAsJsonObject(), MovieTrailer.class);
                        list_trailer.add(movieTrailer);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }

                }
            }
            if (list_trailer != null) {
                return list_trailer;
            } else {
                Log.i(TAG, "list_comment is null");
                return null;

            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("detailfragment","ondestroy");
       if(!DetailActivity.IS_FROM_COLLECT_ACTIVITY) list_comment.clear();
        list_trailer.clear();
    }
    public void LoadContentsFromList(){
        Log.i("detailfragment","loadfrom");
        Bundle argument = getArguments();
        if((argument==null))Log.i("detailfragment","bundle is null");
        if (argument != null) {

            position = argument.getInt("position", 0);
            if (!ContentFragment.getList().isEmpty()){
                String id = ContentFragment.getList().get(position).getId();
                Log.i("fragment", id);
                Log.i("fragment", String.valueOf(list_movive.size()));

                GetCommentTask getCommentTask = new GetCommentTask();
                getCommentTask.execute(id);
                GetTrailerTask getTrailerTask = new GetTrailerTask();
                getTrailerTask.execute(id);
            }
        }
    }
}

