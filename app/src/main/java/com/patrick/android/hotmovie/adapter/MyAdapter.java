package com.patrick.android.hotmovie.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.patrick.android.hotmovie.ContentFragment;
import com.patrick.android.hotmovie.DetailFragment;
import com.patrick.android.hotmovie.R;
import com.patrick.android.hotmovie.TrailerActivity;
import com.patrick.android.hotmovie.db.MovieContact;
import com.patrick.android.hotmovie.module.Movie;
import com.patrick.android.hotmovie.module.MovieSub;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean collected=false;
    private long id;


    public MyAdapter(Context context, List<Movie> list, List<MovieSub>list_comment, int position) {
        super();
        this.context=context;
        this.list=list;
        this.list_comment=list_comment;
        this.positon_of_movie =position;
    }
    List<Movie> list;
    List<MovieSub> list_comment;
   static Context context;
  static  int positon_of_movie;
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
            String date = "上映日期：" + ContentFragment.getList().get(positon_of_movie).getRelease_date();
            String vote = "评分：" + ContentFragment.getList().get(positon_of_movie).getVote_average() + "/10";
            String address = ContentFragment.getList().get(positon_of_movie).getPoster_Path();
            Picasso.with(context).load("http://image.tmdb.org/t/p/w780/" + address).into(((MyAdapter.DetailViewHolder) holder).imageView);
            ( (MyAdapter.DetailViewHolder) holder).textView_overview.setText(ContentFragment.getList().get(positon_of_movie).getOverview());
            ( (MyAdapter.DetailViewHolder) holder).textView_date.setText(date);
            ( (MyAdapter.DetailViewHolder) holder).textView_rate.setText(vote );
//            ( (DetailViewHolder) holder).checkBox.setBackgroundResource(R.drawable.button_star);
            ( (MyAdapter.DetailViewHolder) holder).textView_title.setText(ContentFragment.getList().get(positon_of_movie).getTitle() );
        }
        if(holder instanceof TrailerViewHolder){
            ( (TrailerViewHolder) holder).imageView_trailer.setImageResource(R.drawable.play_db);
            ( (TrailerViewHolder) holder).imageView_trailer_arrow.setImageResource(R.drawable.arrow);
        }
        if(holder instanceof CommentViewHolder){
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
        ImageView imageView_star;
        public DetailViewHolder(View itemView) {
            super(itemView);
           imageView_star=(ImageView)itemView.findViewById(R.id.fragment_detail_collected);
            imageView=(ImageView)itemView.findViewById(R.id.fragment_detail_poster);
            textView_date=(TextView)itemView.findViewById(R.id.fragment_detail_textview_date);
            textView_rate=(TextView)itemView.findViewById(R.id.fragment_detail_textview_rate);
            textView_title=(TextView)itemView.findViewById(R.id.fragment_detail_textview_title);
            textView_overview=(TextView)itemView.findViewById(R.id.fragment_detail_textview_overview);
            imageView_star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Uri uri= MovieContact.MovieEntry.CONTENT_URI;
                    if(!collected){

                        ContentValues contentValues=new ContentValues();
                        contentValues.put("title",ContentFragment.getList().get(positon_of_movie).getTitle());
                        contentValues.put("overview",ContentFragment.getList().get(positon_of_movie).getOverview());
                        contentValues.put("rate",ContentFragment.getList().get(positon_of_movie).getVote_average());
                        contentValues.put("date",ContentFragment.getList().get(positon_of_movie).getRelease_date());

                        id= Long.parseLong(context.getContentResolver().insert(uri,contentValues).getPathSegments().get(1));
                        Log.i("return uri", String.valueOf(context.getContentResolver().insert(uri,contentValues)));
                        collected=true;
                    }
                    else{
                       context.getContentResolver().delete(MovieContact.MovieEntry.buildMovieUri(id),"title=?",new String[]{ContentFragment.getList().get(positon_of_movie).getTitle()});

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
                    List<String> list=new ArrayList<>();
                    for (int i = 0; i < DetailFragment.list_trailer.size(); i++) {
                        String address=DetailFragment.list_trailer.get(i).getKey();
                        list.add(address);
                    }
                    //及时清空，否则会随着打开不同的电影一直累计下去
                    DetailFragment.list_trailer.clear();
                    Intent intent = new Intent(context, TrailerActivity.class);
                    intent.putStringArrayListExtra("address", (ArrayList<String>) list);
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
}
