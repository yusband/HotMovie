package com.patrick.android.hotmovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.patrick.android.hotmovie.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/11/15.
 */

public class CursorRecyclerViewAdapter extends CursorAdapter{
    private  Context mContext;

    public static final int TYPE_DETAIL=1;
    public static final int TYPE_COMMON=0;
    public static final int TYPE_TRAILER=2;
    public static final int TYPE_COMMENT=3;


    public CursorRecyclerViewAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.mContext=context;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return  newView(context,cursor,parent,0);
    }
    public View newView(Context context, Cursor cursor, ViewGroup parent,int type){

        if(type==TYPE_COMMON){
            int layoutId = R.layout.test_cursor_view;
            View view =LayoutInflater.from(context).inflate(layoutId, parent, false);
            CommonViewHolder viewHolder = new CommonViewHolder(view);
            view.setTag(viewHolder);
            Log.i("cursor get position", String.valueOf(cursor.getPosition()));
            return view;}
        if(type==TYPE_DETAIL){
            View view=LayoutInflater.from(context).inflate(R.layout.detail_fragment_info,parent,false);
            DetailViewHolder holder=new DetailViewHolder(view);
            view.setTag(holder);
            return view;
        }
        if(type==TYPE_TRAILER){
            View view=LayoutInflater.from(context).inflate(R.layout.detail_fragment_trailer,parent,false);
            TrailerViewHolder holder=new TrailerViewHolder(view);
            view.setTag(holder);
            return view;
        }
        if(type==TYPE_COMMENT){
            View view=LayoutInflater.from(context).inflate(R.layout.detail_fragment_comment,parent,false);
            CommentViewHolder holder=new CommentViewHolder(view);
            view.setTag(holder);
            return view;
        }
        return null;
    }

    public class CommonViewHolder extends RecyclerView.ViewHolder {
        public final ImageView iconView;
        public final TextView titleView;
        public final TextView dateView;


        public CommonViewHolder(View view) {
            super(view);
            iconView = (ImageView) view.findViewById(R.id.test_cursor_view_poster);
            dateView = (TextView) view.findViewById(R.id.test_cursor_view_date);
            titleView = (TextView) view.findViewById(R.id.test_cursor_view_title);

        }

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
        ImageView imageView_trailer_arrow;
        public TrailerViewHolder(View itemView) {
            super(itemView);
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

    public void bindView(View view, Context context, Cursor cursor,int type) {

        if(type==TYPE_COMMON){
            CommonViewHolder viewHolder = (CommonViewHolder) view.getTag();
            Picasso.with(context).load("http://image.tmdb.org/t/p/w154/" + cursor.getString(6)).into(viewHolder.iconView);
            viewHolder.titleView.setText(cursor.getString(1));
            viewHolder.dateView.setText(cursor.getString(2));

         }
        if(type==TYPE_DETAIL){
            DetailViewHolder viewHolder = (DetailViewHolder) view.getTag();
            Picasso.with(context).load("http://image.tmdb.org/t/p/w154/" + cursor.getString(6)).into(viewHolder.imageView);
            viewHolder.textView_title.setText(cursor.getString(1));
            viewHolder.textView_date.setText("上映日期："+cursor.getString(2));
            viewHolder.textView_rate.setText("评分: "+cursor.getString(3));
            viewHolder.textView_overview.setText(cursor.getString(5));
            viewHolder.textView_length.setText("时长: "+cursor.getString(8)+"mins");
        }
        if(type==TYPE_TRAILER){
           TrailerViewHolder viewHolder=(TrailerViewHolder)view.getTag();
            viewHolder.imageView_trailer.setImageResource(R.drawable.play_db);
            viewHolder.imageView_trailer_arrow.setImageResource(R.drawable.arrow);

        }
        if(type==TYPE_COMMENT){
           CommentViewHolder viewHolder=(CommentViewHolder)view.getTag();
            viewHolder.textView_comment.setText(cursor.getString(7));
            viewHolder.textView_comment_author.setText("");

        }


    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        bindView(view,context,cursor,0);
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