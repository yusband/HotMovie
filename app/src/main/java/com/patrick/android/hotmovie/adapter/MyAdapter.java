package com.patrick.android.hotmovie.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.patrick.android.hotmovie.R;
import com.patrick.android.hotmovie.db.MovieContact;
import com.patrick.android.hotmovie.module.Movie;
import com.patrick.android.hotmovie.module.MovieSub;
import com.patrick.android.hotmovie.ui.ContentFragment;
import com.patrick.android.hotmovie.ui.DetailActivity;
import com.patrick.android.hotmovie.ui.DetailFragment;
import com.patrick.android.hotmovie.ui.TrailerActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean collected=false;
    private long id;
    public static final String EXTERNAL_PATH="/storage/emulated/0/";
    List<Movie> list;
    List<MovieSub> list_comment;
    static Context context;
    static  int positon_of_movie;

    public MyAdapter(Context context, List<Movie> list, List<MovieSub>list_comment, int position) {
        super();
        this.context=context;
        this.list=list;
        this.list_comment=list_comment;
        Log.i("myadapter", String.valueOf(list_comment.size()));
        this.positon_of_movie =position;
        if(DetailActivity.IS_FROM_COLLECT_ACTIVITY)decideNumOfComment();
    }
    public void decideNumOfComment(){
//        Log.i("decide1",list_comment.get(positon_of_movie).getContent());
          String[] a=convertStringToArray((list_comment.get(positon_of_movie).getContent()));
          list_comment =convertArrayToList(convertStringToArray((list_comment.get(positon_of_movie).getContent())));
          Log.i("decide", String.valueOf(list_comment.size()));
    }
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==0){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_fragment_info,parent,false);
            DetailViewHolder holder=new DetailViewHolder(view);
            return holder;
        }
        else  if(viewType==1){
            return new MyAdapter.TrailerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_fragment_trailer,parent,false));
        }
            else return new MyAdapter.CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_fragment_comment,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DetailViewHolder) {
            Cursor cursor= context.getContentResolver().query(MovieContact.MovieEntry.CONTENT_URI, new String[]{"title"},"title=?",new String[]{list.get(positon_of_movie).getTitle()},null);
            if(cursor.moveToNext()){
            if(cursor.getString(cursor.getColumnIndex("title"))!=null) {
                collected=true;
                ((DetailViewHolder) holder).imageView_star.setImageResource(R.drawable.ic_star_yellow_a700_24dp);
                ((DetailViewHolder) holder).textView_collected.setText("已收藏");
                Log.i("cursor", String.valueOf(cursor.getCount()));
                Log.i("cursor", cursor.getString(cursor.getColumnIndex("title")));
            }
            }

            else  {
                collected=false;
                ( (DetailViewHolder) holder).imageView_star.setImageResource(R.drawable.ic_star_white_24dp);
                ((DetailViewHolder) holder).textView_collected.setText("点击收藏");
                Log.i("cursor","is null");
            }
            cursor.close();
            String date = "上映日期：" +list.get(positon_of_movie).getRelease_date();
            String vote = "评分：" + list.get(positon_of_movie).getVote_average() + "/10";
            if(!collected){
                String address = list.get(positon_of_movie).getPoster_Path();
            Picasso.with(context).load("http://image.tmdb.org/t/p/w780/" + address).into(((MyAdapter.DetailViewHolder) holder).imageView);
            }
            if(collected){
                Picasso.with(context).load("file://"+list.get(positon_of_movie).getLocal_path()).into(((MyAdapter.DetailViewHolder) holder).imageView);
            }
            ( (MyAdapter.DetailViewHolder) holder).textView_overview.setText(list.get(positon_of_movie).getOverview());
            ( (MyAdapter.DetailViewHolder) holder).textView_date.setText(date);
            ( (MyAdapter.DetailViewHolder) holder).textView_rate.setText(vote );

            ( (MyAdapter.DetailViewHolder) holder).textView_title.setText(list.get(positon_of_movie).getTitle() );

        }
        if(holder instanceof TrailerViewHolder){
            ( (TrailerViewHolder) holder).imageView_trailer.setImageResource(R.drawable.play_db);
            ( (TrailerViewHolder) holder).imageView_trailer_arrow.setImageResource(R.drawable.arrow);
        }
        if(holder instanceof CommentViewHolder){
            Log.i("myadaptercomment", String.valueOf(list_comment.size()));
            if(list_comment.size()==0)
                ( (CommentViewHolder) holder).textView_comment.setText("no comment" );
            else {
                //-2：在detailfragment的布局使用recyclerview，按照从上至下的顺序，在评论之前还有两个单独的view（详细信息和预告片）

                ((CommentViewHolder) holder).textView_comment.setText(list_comment.get(position - 2).getContent());
                ((CommentViewHolder) holder).textView_comment_author.setText(list_comment.get(position - 2).getAuthor());
            }
        }
    }
    public  class DetailViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView_title;
        TextView textView_rate;
        TextView textView_date;
        TextView textView_overview;
        TextView textView_collected;
        ImageView imageView_star;
        public DetailViewHolder(View itemView) {
            super(itemView);
            imageView_star=(ImageView)itemView.findViewById(R.id.fragment_detail_collected);
            imageView=(ImageView)itemView.findViewById(R.id.fragment_detail_poster);
            textView_date=(TextView)itemView.findViewById(R.id.fragment_detail_textview_date);
            textView_rate=(TextView)itemView.findViewById(R.id.fragment_detail_textview_rate);
            textView_title=(TextView)itemView.findViewById(R.id.fragment_detail_textview_title);
            textView_overview=(TextView)itemView.findViewById(R.id.fragment_detail_textview_overview);
            textView_collected=(TextView)itemView.findViewById(R.id.fragment_detail_textview_collected);

            imageView_star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Uri uri= MovieContact.MovieEntry.CONTENT_URI;
                    if(!collected){
                        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                        Bitmap bitmap = drawable.getBitmap();
                        File sdCardDirectory = Environment.getExternalStorageDirectory();
                        File image = new File(sdCardDirectory, ContentFragment.getList().get(positon_of_movie).getTitle()+".png");
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
                            Toast.makeText(context, "Image saved with success",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context,
                                    "Error during image saving", Toast.LENGTH_LONG).show();
                        }
                        ContentValues contentValues=new ContentValues();
                        contentValues.put("title",ContentFragment.getList().get(positon_of_movie).getTitle());
                        contentValues.put("overview",ContentFragment.getList().get(positon_of_movie).getOverview());
                        contentValues.put("rate",ContentFragment.getList().get(positon_of_movie).getVote_average());
                        contentValues.put("date",ContentFragment.getList().get(positon_of_movie).getRelease_date());
                        contentValues.put("path",EXTERNAL_PATH+ ContentFragment.getList().get(positon_of_movie).getTitle()+".png");
                        contentValues.put("comment",convertArrayToString(convertListToArray(DetailFragment.list_comment)));
                        id= Long.parseLong(context.getContentResolver().insert(uri,contentValues).getPathSegments().get(1));
                        Log.i("current id ", String.valueOf(id));
                        collected=true;
                        imageView_star.setImageResource(R.drawable.ic_star_yellow_a700_24dp);
                        textView_collected.setText("已收藏");
                    }
                    else{
                        context.getContentResolver().delete(MovieContact.MovieEntry.buildMovieUri(id),"title=?",new String[]{ContentFragment.getList().get(positon_of_movie).getTitle()});
                        collected=false;
                        imageView_star.setImageResource(R.drawable.ic_star_white_24dp);
                        textView_collected.setText("点击收藏");
                    }
                    }
                }
            );

        }
    }
    public class TrailerViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView_trailer;
        ImageView imageView_trailer_arrow;
        public TrailerViewHolder(View itemView) {
            super(itemView);
            imageView_trailer=(ImageView)itemView.findViewById(R.id.fragment_detail_imageview_trailer);
            imageView_trailer_arrow=(ImageView)itemView.findViewById(R.id.fragment_detail_imageview_trailer_arrow);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, TrailerActivity.class);
                    context.startActivity(intent);

                }
                });
        }
    }
    public static class CommentViewHolder extends RecyclerView.ViewHolder{



        TextView textView_comment;
        TextView textView_comment_author;
        public CommentViewHolder(View itemView) {
            super(itemView);
            textView_comment=(TextView)itemView.findViewById(R.id.fragment_detail_textview_comment);
            textView_comment_author=(TextView)itemView.findViewById(R.id.fragment_detail_textview_comment_author);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position<=1){
            return position;
        }
        else return  2;
    }

    @Override
    public int getItemCount() {
        return list_comment.size()+2;
    }


    public static String strSeparator = "__,__";
    public static String[] convertListToArray(List<MovieSub> list)
    {String[] array=new String[list.size()];
        for (int i=0;i<list.size();i++)array[i]=list.get(i).getContent();
        return  array;
    }
    public static List<MovieSub> convertArrayToList(String[] array)
    { List<MovieSub> list=new ArrayList<>();
        for (int i=0;i<array.length;i++){
            MovieSub movieSub=new MovieSub();
            movieSub.setContent(array[i]);
            list.add(movieSub);
        }
        return  list;
    }

    public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
    public static String[] convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
    }

}
