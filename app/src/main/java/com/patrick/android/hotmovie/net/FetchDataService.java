package com.patrick.android.hotmovie.net;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.patrick.android.hotmovie.BuildConfig;
import com.patrick.android.hotmovie.db.MovieContact;
import com.patrick.android.hotmovie.module.Movie;
import com.patrick.android.hotmovie.module.MovieSub;
import com.patrick.android.hotmovie.module.MovieTrailer;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static android.content.ContentValues.TAG;

public class FetchDataService extends IntentService{
    public static final String ORDER_TAG_ONE="top_rated";
    public static final String ORDER_TAG_TWO="popular";
    List<String> popular_movies_id_list=new ArrayList<>();
    List<String> top_rated_movie_id_list=new ArrayList<>();
    public static String strSeparator = "__,__";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FetchDataService(String name) {
        super(name);
    }
    public FetchDataService(){
        this(null);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
    if(intent!=null){
    }
        fetchTwentyMovies(ORDER_TAG_ONE);
        fetchTwentyMovies(ORDER_TAG_TWO);
        if((top_rated_movie_id_list !=null)&&(top_rated_movie_id_list.size()!=0))
        {
            fetchTrailerData(top_rated_movie_id_list,MovieContact.TopMovieEntry.CONTENT_URI);
            fetchCommentData(top_rated_movie_id_list,MovieContact.TopMovieEntry.CONTENT_URI);
            fetchLength(top_rated_movie_id_list,MovieContact.TopMovieEntry.CONTENT_URI);
        }
        if((popular_movies_id_list !=null)&&(popular_movies_id_list.size()!=0))
        {
            fetchTrailerData(popular_movies_id_list,MovieContact.PopularMovieEntry.CONTENT_URI);
            fetchCommentData(popular_movies_id_list,MovieContact.PopularMovieEntry.CONTENT_URI);
            fetchLength(popular_movies_id_list,MovieContact.PopularMovieEntry.CONTENT_URI);
        }
    }

    public void fetchTwentyMovies(String order){
//                                 http://api.themoviedb.org/3/movie/<movie_id>+ "?api_key=" + BuildConfig.MY_API_KEY;
//        final String ADDRESS = "http://api.themoviedb.org/3/movie/" + id + "/videos?api_key="+BuildConfig.MY_API_KEY;
//        final String ADDRESS = "http://api.themoviedb.org/3/movie/" + id + "/reviews?api_key="BuildConfig.MY_API_KEY;
            final String ADDRESS = "http://api.themoviedb.org/3/movie/" + order + "?api_key=" + BuildConfig.MY_API_KEY;
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
                Vector<ContentValues> movieVector = new Vector<>(array.size());
                for (int j = 0; j < array.size(); j++) {
                    Gson gson = new Gson();
                    Movie movie = gson.fromJson(array.get(j).getAsJsonObject(), Movie.class);

                    ContentValues movieValues = new ContentValues();
                    if ((order.length() != 0) && TextUtils.equals(order, "popular")) {
                        movieValues.put(MovieContact.PopularMovieEntry.COLUMN_TITLE, movie.getTitle());
                        movieValues.put(MovieContact.PopularMovieEntry.COLUMN_RELEASE_DATE, movie.getRelease_date());
                        movieValues.put(MovieContact.PopularMovieEntry.COLUMN_VOTE, movie.getVote_average());
                        movieValues.put(MovieContact.PopularMovieEntry.COLUMN_NUMBER, movie.getId());
                        movieValues.put(MovieContact.PopularMovieEntry.COLUMN_OVERVIEW, movie.getOverview());
                        movieValues.put(MovieContact.PopularMovieEntry.COLUMN_PATH, movie.getPoster_Path());
                        popular_movies_id_list.add(movie.getId());
                    }
                    if ((order.length() != 0) && TextUtils.equals(order, "top_rated")) {
                        movieValues.put(MovieContact.TopMovieEntry.COLUMN_TITLE, movie.getTitle());
                        movieValues.put(MovieContact.TopMovieEntry.COLUMN_RELEASE_DATE, movie.getRelease_date());
                        movieValues.put(MovieContact.TopMovieEntry.COLUMN_VOTE, movie.getVote_average());
                        movieValues.put(MovieContact.TopMovieEntry.COLUMN_NUMBER, movie.getId());
                        movieValues.put(MovieContact.TopMovieEntry.COLUMN_OVERVIEW, movie.getOverview());
                        movieValues.put(MovieContact.TopMovieEntry.COLUMN_PATH, movie.getPoster_Path());
                       top_rated_movie_id_list.add(movie.getId());
                    }
                    movieVector.add(movieValues);

                }
                int inserted = 0;
                if (movieVector.size() > 0) {
                    ContentValues[] cvArray = new ContentValues[movieVector.size()];
                    movieVector.toArray(cvArray);
                    Log.d(TAG, "Before FetchTask Complete. " + inserted + " Inserted");
                    if ((order.length() != 0) && TextUtils.equals(order, "popular")) {
                        inserted = getContentResolver().bulkInsert(MovieContact.PopularMovieEntry.CONTENT_URI, cvArray);
                    }
                    if ((order.length() != 0) && TextUtils.equals(order, "top_rated")) {
                        inserted = getContentResolver().bulkInsert(MovieContact.TopMovieEntry.CONTENT_URI, cvArray);
                    }
                }
                Log.d(TAG, "FetchWeatherTask Complete. " + inserted + " Inserted");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

            }
        }
    public void fetchTrailerData(List<String> list,Uri uri){
        for(String id:list){
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
            StringBuffer trailers =new StringBuffer();
            for (int j = 0; j < array.size(); j++) {
                Gson gson = new Gson();
               trailers.append(gson.fromJson(array.get(j).getAsJsonObject(), MovieTrailer.class).getName()).append(strSeparator).append(gson.fromJson(array.get(j).getAsJsonObject(), MovieTrailer.class).getKey()).append(strSeparator);
            }
            ContentValues contentValues=new ContentValues();
            contentValues.put("trailer", String.valueOf(trailers));
            int id_return= (getContentResolver().update(uri,contentValues,"number=?",new String[]{id}));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

        }}
    }
    public void fetchCommentData(List<String> list,Uri uri) {
        for (String id : list) {
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
                StringBuffer comments = new StringBuffer();

                for (int j = 0; j < array.size(); j++) {
                    Gson gson = new Gson();
                    comments.append(gson.fromJson(array.get(j).getAsJsonObject(), MovieSub.class).getAuthor()).append(strSeparator).append(gson.fromJson(array.get(j).getAsJsonObject(), MovieSub.class).getContent()).append(strSeparator);
                }

                ContentValues contentValues = new ContentValues();
                contentValues.put("comment", String.valueOf(comments));
                int id_return = (getContentResolver().update(uri, contentValues, "number=?", new String[]{id}));
                Log.i("service_comment", String.valueOf(id_return));

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

            }
        }
    }
    public void fetchLength(List<String> list,Uri uri){
      for(String id:list) {
          final String ADDRESS = "http://api.themoviedb.org/3/movie/" + id + "?api_key=" + BuildConfig.MY_API_KEY;
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
String length=object.get("runtime").toString();
//              JsonPrimitive primitive = object.getAsJsonPrimitive("runtime");
//              String length = String.valueOf(primitive.getAsCharacter());
              Log.i("intentservice ", "length= " + length);
              ContentValues contentValues = new ContentValues();
              contentValues.put("length", length);
              Log.i("service_length", id);
              int id_return = (getContentResolver().update(uri, contentValues, "number=?", new String[]{id}));
              Log.i("service_length", String.valueOf(id_return));
          } catch (ProtocolException e) {
              e.printStackTrace();
          } catch (MalformedURLException e) {
              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          }

      }
    }
public void saveImage(String address,String identifier){

    ImageView imageView=null;
    Picasso.with(this).load("http://image.tmdb.org/t/p/w780/" + address).into(imageView);
    BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
    Bitmap bitmap = drawable.getBitmap();
    File sdCardDirectory = Environment.getExternalStorageDirectory();
    File image = new File(sdCardDirectory,identifier+".png");
    Log.i("路径",image.getPath());
    boolean success = false;
    // Encode the file as a PNG image.
    FileOutputStream outStream;
    try {
        outStream = new FileOutputStream(image);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        /* 100 to keep full quality of the image */
        outStream.flush();
        outStream.close();
        success = true;
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    if (success) {
        Toast.makeText(this, "Image saved with success",
                Toast.LENGTH_LONG).show();
    } else {
        Toast.makeText(this,
                "Error during image saving", Toast.LENGTH_LONG).show();
    }
}
        }













