package com.patrick.android.hotmovie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/27.
 */
public class ContentFragment extends Fragment {

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragmet_content,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_fragment_content_item_settings:
                startActivity(new Intent(getActivity(),SettingActivity.class));
                    return  true;
            default:
            return super.onOptionsItemSelected(item);
        }
        }

    public static List<Movie> getList() {
        return list;
    }
private GridView gridview;



    private static List<Movie> list = new ArrayList();
    private final String TAG = getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_content, container, false);
        setHasOptionsMenu(true);
        /*初始化gridview并加上响应事件，捕获点击的位置放入intent，
        启动DetailActivity
         */
        gridview = (GridView) rootView.findViewById(R.id.gridview);
//        gridview.setAdapter(imageAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("position",position);
                Toast.makeText(getActivity(),"CLICKED",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        return rootView;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new ParseDataTask().execute("popular");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        //清空列表，以保证不会重复在gridview中加载图片
        list.clear();
//        gridview.setAdapter(null);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort_order=preferences.getString("pref_sortOrder","popular");
        ParseDataTask parseDataTask=new ParseDataTask();
        parseDataTask.execute(sort_order);



    }
    public class ImageAdapter extends BaseAdapter {
        private final String TAG=getClass().getSimpleName();
        private Context mContext;
        public ImageAdapter(Context c) {
            mContext = c;
        }


        @Override
        public int getCount() {
            return list.size();

        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                Configuration configuration=getResources().getConfiguration();
                //判断是横屏还是竖屏，并进行布局参数的改动，但这里...并没有做出区别
                if(configuration.orientation==Configuration.ORIENTATION_PORTRAIT){
                    imageView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 800));
                }
                if(configuration.orientation==Configuration.ORIENTATION_LANDSCAPE){
                    imageView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 800));
                }
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(0, 0, 0, 0);

            } else {
                imageView = (ImageView) convertView;
            }
                String posterAddress=list.get(position).getPoster_Path();

                Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342/"+posterAddress).into(imageView);
           int width= imageView.getMeasuredWidth();
            int height=imageView.getMeasuredHeight();
//            Toast.makeText(getActivity(),"width"+width+"height"+height,Toast.LENGTH_LONG).show();
                return imageView;

        }




    }


    public class ParseDataTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List doInBackground(String... params) {
            if (params.length!=0){

            String sort_order=params[0];
            final String ADDRESS = "http://api.themoviedb.org/3/movie/"+sort_order+"?api_key=YOUR_API_KEY";
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
//                object = object.getAsJsonObject("results");
                JsonArray array = object.getAsJsonArray("results");

                for (int j = 0; j < array.size(); j++) {
                    Gson gson = new Gson();
//                    List<Movie> moviesList=gson.fromJson(array.get(j).getAsJsonObject(), new TypeToken<List<Movie>>(){}.getType());
                    Movie movie = gson.fromJson(array.get(j).getAsJsonObject(), Movie.class);
                    list.add(movie);
//                    object=array.get(j).getAsJsonObject();
//                    String overview=object.get("overview").toString();
//                    String title=object.get("original_title").toString();
//                    String path=object.get("poster_path").toString();
//                    String pupolarity=object.get("popularity").toString();
//                    String rates=object.get("vote_average").toString();

                }


               String size= String.valueOf(list.size());



            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

            }}
            if (list != null) {
                return list;
            } else {
                Log.i(TAG, "list is null");
                return null;

            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            //尽量不要在fragment中设置“需要传入context类型的参数”的成员变量，容易引发空指针错误
            gridview.setAdapter(new ImageAdapter(getActivity()));
        }
    }
}
