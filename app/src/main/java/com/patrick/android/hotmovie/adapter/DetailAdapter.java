package com.patrick.android.hotmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.patrick.android.hotmovie.R;
import com.patrick.android.hotmovie.net.FetchDataService;
import com.patrick.android.hotmovie.ui.DetailFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/12/4.
 */

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static String strSeparator = "__,__";
    private String[] comments=null;
    private String[] trailers=null;
    public  static final String KEY="title";
    public  static final String ACTION_INSERT="insert";
    public  static final String ACTION_DELETE="delete";
    public CursorRecyclerViewAdapter getCursorRecyclerViewAdapter() {
        return cursorRecyclerViewAdapter;
    }

    CursorRecyclerViewAdapter cursorRecyclerViewAdapter=null;
    public static final int TYPE_DETAIL=0;
    public static final int TYPE_TRAILER=1;
    public static final int TYPE_COMMENT=2;
    Context mContext;
int movie_position=0;
    public DetailAdapter(Context context,Cursor c,int position) {
        mContext=context;
        cursorRecyclerViewAdapter=new CursorRecyclerViewAdapter(context,c,0);
        this.movie_position=position;
        if(c!=null) cursorRecyclerViewAdapter.getCursor().moveToPosition(position);
        comments=cursorRecyclerViewAdapter.getCursor().getString(7).split(strSeparator);
        trailers=cursorRecyclerViewAdapter.getCursor().getString(9).split(strSeparator);
        Log.i("trailer",cursorRecyclerViewAdapter.getCursor().getString(9));
        Log.i("commentslength",comments[0]);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=cursorRecyclerViewAdapter.newView(cursorRecyclerViewAdapter.getCursor(),parent,viewType);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        cursorRecyclerViewAdapter.getCursor().moveToPosition(movie_position);
        Log.i("contentfragment", "movie position is " + movie_position);
      if(position==0){
                cursorRecyclerViewAdapter.bindView(holder.itemView, mContext, cursorRecyclerViewAdapter.getCursor(), TYPE_DETAIL,position);}
      else if(position<=trailers.length/2) {
          cursorRecyclerViewAdapter.bindView(holder.itemView, mContext, cursorRecyclerViewAdapter.getCursor(), TYPE_TRAILER, position);
      }

     else {
                cursorRecyclerViewAdapter.bindView(holder.itemView, mContext, cursorRecyclerViewAdapter.getCursor(), TYPE_COMMENT,position);

        }
    }

    @Override
    public int getItemCount() {

        int count = 1;
        if (comments.length > 1) {
            count = count + comments.length / 2;
        }
        if (trailers.length > 1) {
            count = count + trailers.length / 2;
        }
        return count;

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)return TYPE_DETAIL;
        else if((trailers.length>1)&&(position<=(trailers.length/2))){
        return TYPE_TRAILER;
        }

        else return TYPE_COMMENT;
    }

    public class CursorRecyclerViewAdapter extends CursorAdapter {private  Context mContext;
        public CursorRecyclerViewAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
            mContext=context;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return null;
        }

        public View newView(Cursor cursor, ViewGroup parent, int type) {
            Context context = mContext;
            if (type == TYPE_DETAIL) {

                View view = LayoutInflater.from(context).inflate(R.layout.detail_fragment_info, parent, false);
                DetailViewHolder holder = new DetailViewHolder(view);
                view.setTag(holder);
                return view;
            }
            if (type == TYPE_TRAILER) {
                View view = LayoutInflater.from(context).inflate(R.layout.detail_fragment_trailer, parent, false);
                TrailerViewHolder holder = new TrailerViewHolder(view);
                view.setTag(holder);
                return view;
            }
            if (type == TYPE_COMMENT) {
                View view = LayoutInflater.from(context).inflate(R.layout.detail_fragment_comment, parent, false);
                CommentViewHolder holder = new CommentViewHolder(view);
                view.setTag(holder);
                return view;
            }
            return null;
        }





        public  class DetailViewHolder extends RecyclerView.ViewHolder{
            ImageView imageView;
            TextView textView_title;
            TextView textView_rate;
            TextView textView_date;
            TextView textView_overview;
            TextView textView_collected;
            TextView textView_length;
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
                textView_length=(TextView)itemView.findViewById(R.id.fragment_detail_textview_length);


            }
        }
        public  class TrailerViewHolder extends RecyclerView.ViewHolder{
            ImageView imageView_trailer;
            TextView textView_trailer_info;
            ImageView imageView_trailer_arrow;
            public TrailerViewHolder(View itemView) {
                super(itemView);
                textView_trailer_info=(TextView)itemView.findViewById(R.id.fragment_detail_textview_trailer);
                imageView_trailer=(ImageView)itemView.findViewById(R.id.fragment_detail_imageview_trailer);
                imageView_trailer_arrow=(ImageView)itemView.findViewById(R.id.fragment_detail_imageview_trailer_arrow);

            }
        }
        public  class CommentViewHolder extends RecyclerView.ViewHolder{

            TextView textView_comment;
            TextView textView_comment_author;
            public CommentViewHolder(View itemView) {
                super(itemView);
                textView_comment=(TextView)itemView.findViewById(R.id.fragment_detail_textview_comment);
                textView_comment_author=(TextView)itemView.findViewById(R.id.fragment_detail_textview_comment_author);
            }
        }

        public void bindView(View view, final Context context, final Cursor cursor, int type, final int position) {




            if(type==TYPE_DETAIL){
                final DetailViewHolder viewHolder = (DetailViewHolder) view.getTag();
                Picasso.with(context).load("http://image.tmdb.org/t/p/w154/" + cursor.getString(6)).into(viewHolder.imageView);
                viewHolder.textView_title.setText(cursor.getString(1));
                viewHolder.textView_date.setText("上映日期："+cursor.getString(2));
                viewHolder.textView_rate.setText("评分: "+cursor.getString(3));
                viewHolder.imageView_star.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!DetailFragment.IS_FAVOURED){
                            Log.i("dAdapter", "favour");
                            viewHolder.imageView_star.setImageResource(R.drawable.ic_star_yellow_a700_24dp);
                            viewHolder.textView_collected.setText(R.string.favoured);
                            DetailFragment.IS_FAVOURED=true;
                            Intent intent=new Intent(context, FetchDataService.class);
                            intent.putExtra(KEY,ACTION_INSERT);
                            Bundle bundle=new Bundle();
                            bundle.putString(String.valueOf(DetailFragment.COL_TITLE),cursor.getString(DetailFragment.COL_TITLE));
                            bundle.putString(String.valueOf(DetailFragment.COL_RELEASE_DATE),cursor.getString(DetailFragment.COL_RELEASE_DATE));
                            bundle.putString(String.valueOf(DetailFragment.COL_VOTE),cursor.getString(DetailFragment.COL_VOTE));
                            bundle.putString(String.valueOf(DetailFragment.COL_NUMBER),cursor.getString(DetailFragment.COL_NUMBER));
                            bundle.putString(String.valueOf(DetailFragment.COL_OVERVIEW),cursor.getString(DetailFragment.COL_OVERVIEW));
                            bundle.putString(String.valueOf(DetailFragment.COL_PATH),cursor.getString(DetailFragment.COL_PATH));
                            bundle.putString(String.valueOf(DetailFragment.COL_COMMENT),cursor.getString(DetailFragment.COL_COMMENT));
                            bundle.putString(String.valueOf(DetailFragment.COL_LENGTH),cursor.getString(DetailFragment.COL_LENGTH));
                            bundle.putString(String.valueOf(DetailFragment.COL_TRAILER),cursor.getString(DetailFragment.COL_TRAILER));
                            intent.putExtra("data",bundle);
                            context.startService(intent);

                        }
                      else if(DetailFragment.IS_FAVOURED){
                            Log.i("dAdapter", "unfavour");
                            viewHolder.imageView_star.setImageResource(R.drawable.ic_star_white_24dp);
                            viewHolder.textView_collected.setText(R.string.not_favoured);
                            DetailFragment.IS_FAVOURED=false;
                            Intent intent=new Intent(context, FetchDataService.class);
                            intent.putExtra(KEY,ACTION_DELETE);
                            intent.putExtra("name",cursor.getString(DetailFragment.COL_TITLE));
                            context.startService(intent);

                        }
                    }
                });
                if(DetailFragment.IS_FAVOURED){
                    viewHolder.imageView_star.setImageResource(R.drawable.ic_star_yellow_a700_24dp);
                    viewHolder.textView_collected.setText(R.string.favoured);
                }
                else {
                    viewHolder.imageView_star.setImageResource(R.drawable.ic_star_white_24dp);
                    viewHolder.textView_collected.setText(R.string.not_favoured);}
                    viewHolder.textView_overview.setText(cursor.getString(5));
                    viewHolder.textView_length.setText("时长: "+cursor.getString(8)+"mins");
            }
            if(type==TYPE_TRAILER){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =new Intent(Intent.ACTION_VIEW);
                        intent.setData( Uri.parse("http://www.youtube.com/watch?v="+trailers[(position-1)*2+1]));
                        String title = context.getResources().getString(R.string.chooser_title);
// Create intent to show the chooser dialog
                        Intent chooser = Intent.createChooser(intent, title);
                        if (intent.resolveActivity(context.getPackageManager()) != null) {
                            context.startActivity(chooser);
                        }

                        PackageManager packageManager = context.getPackageManager();
                        List activities = packageManager.queryIntentActivities(intent,
                                PackageManager.MATCH_DEFAULT_ONLY);
                        boolean isIntentSafe = activities.size() > 0;
                        Log.i("trailer intent", String.valueOf(activities.size()));
//                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+trailers[(position-1)*2+1])));
                    }




                });
               TrailerViewHolder viewHolder=(TrailerViewHolder)view.getTag();
                viewHolder.imageView_trailer.setImageResource(R.drawable.yt_logo);
                viewHolder.textView_trailer_info.setText(trailers[(position-1)*2]);
                viewHolder.imageView_trailer_arrow.setImageResource(R.drawable.arrow);

            }
            if(type==TYPE_COMMENT){
Log.i("detailbindview","comment");
                CommentViewHolder viewHolder=(CommentViewHolder)view.getTag();
                if(comments.length>1){
                viewHolder.textView_comment_author.setText(comments[(position-1-trailers.length/2)*2]);
                viewHolder.textView_comment.setText(comments[(position-1-trailers.length/2)*2+1]);}
                else {
                    viewHolder.textView_comment.setText("暂无评论...o(╯□╰)o");
                }

            }


        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            bindView(view,context,cursor,0,0);
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
    public static String[] convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
    }
}
