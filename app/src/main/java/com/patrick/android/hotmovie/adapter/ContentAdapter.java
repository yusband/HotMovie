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
import android.widget.RatingBar;
import android.widget.TextView;

import com.patrick.android.hotmovie.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/11/23.
 */

public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public interface OnItemClickedLandListener{
        public void onItemClickedLand(int position,String table,String title);

    };
    OnItemClickedLandListener itemClickedLandListener;
    public CursorRecyclerViewAdapter getCursorRecyclerViewAdapter() {
        return cursorRecyclerViewAdapter;
    }

    CursorRecyclerViewAdapter cursorRecyclerViewAdapter=null;
    Context mContext;

    public static final int TYPE_COMMON=0;

    private int movie_position;

    public ContentAdapter(Cursor c, Context context) {
        cursorRecyclerViewAdapter=new CursorRecyclerViewAdapter(context,c,0);
        itemClickedLandListener= (OnItemClickedLandListener)context;
        mContext=context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=cursorRecyclerViewAdapter.newView(mContext,cursorRecyclerViewAdapter.getCursor(),parent);
        return new ViewHolder(view) ;

    }
    private class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,  final int position) {

       {
            // Passing the binding operation to cursor loader
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    cursorRecyclerViewAdapter.getCursor().moveToPosition(position);
                    itemClickedLandListener.onItemClickedLand(position,mContext.getSharedPreferences("sort_order", Context.MODE_PRIVATE).getString("rate", ""),cursorRecyclerViewAdapter.getCursor().getString(1));
//                    Intent intent = new Intent(mContext, DetailActivity.class);
//                    intent.putExtra("title", cursorRecyclerViewAdapter.getCursor().getString(1));
//                    intent.putExtra("position", position);
//                    intent.putExtra("table", mContext.getSharedPreferences("sort_order", Context.MODE_PRIVATE).getString("rate", ""));
//                    mContext.startActivity(intent);

                    Log.i("contentfragment", "position is " + position);
                    Log.i("contentfragment", "title is " + cursorRecyclerViewAdapter.getCursor().getString(1));
                    Log.i("contentfragment", "table name" + mContext.getSharedPreferences("sort_order", Context.MODE_PRIVATE).getString("rate", ""));

                }
            });
            cursorRecyclerViewAdapter.getCursor().moveToPosition(position);
            //EDITED: added this line as suggested in the comments below, thanks :)
            cursorRecyclerViewAdapter.bindView(holder.itemView, mContext, cursorRecyclerViewAdapter.getCursor(), TYPE_COMMON);
        }
        }

    @Override
    public int getItemCount() {

        return cursorRecyclerViewAdapter.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public class CursorRecyclerViewAdapter extends CursorAdapter {
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
            int layoutId = R.layout.test_cursor_view;
            View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
           CommonViewHolder viewHolder =new  CommonViewHolder(view);
            view.setTag(viewHolder);
            Log.i("cursor get position", String.valueOf(cursor.getPosition()));
              return view;}



        public class CommonViewHolder extends RecyclerView.ViewHolder {
            public final ImageView iconView;
            public final TextView titleView;
            public final TextView dateView;
            private final RatingBar ratingBar;


            public CommonViewHolder(View view) {
                super(view);
                iconView = (ImageView) view.findViewById(R.id.test_cursor_view_poster);
                dateView = (TextView) view.findViewById(R.id.test_cursor_view_date);
                titleView = (TextView) view.findViewById(R.id.test_cursor_view_title);
                ratingBar=(RatingBar)view.findViewById(R.id.test_cursor_view_rate);
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


              CommonViewHolder viewHolder = (CommonViewHolder) view.getTag();
                Picasso.with(context).load("http://image.tmdb.org/t/p/w154/" + cursor.getString(6)).into(viewHolder.iconView);
                viewHolder.titleView.setText(cursor.getString(1));
                viewHolder.dateView.setText(cursor.getString(2));
            Log.i("rate",cursor.getString(3));
            viewHolder.ratingBar.setRating(Float.parseFloat(cursor.getString(3))/2);


//            if(type==TYPE_DETAIL){
//                com.patrick.android.hotmovie.adapter.CursorRecyclerViewAdapter.DetailViewHolder viewHolder = (com.patrick.android.hotmovie.adapter.CursorRecyclerViewAdapter.DetailViewHolder) view.getTag();
//                Picasso.with(context).load("http://image.tmdb.org/t/p/w154/" + cursor.getString(6)).into(viewHolder.imageView);
//                viewHolder.textView_title.setText(cursor.getString(1));
//                viewHolder.textView_date.setText("上映日期："+cursor.getString(2));
//                viewHolder.textView_rate.setText("评分: "+cursor.getString(3));
//                viewHolder.textView_overview.setText(cursor.getString(5));
//                viewHolder.textView_length.setText("时长: "+cursor.getString(8)+"mins");
//            }
//            if(type==TYPE_TRAILER){
//                com.patrick.android.hotmovie.adapter.CursorRecyclerViewAdapter.TrailerViewHolder viewHolder=(com.patrick.android.hotmovie.adapter.CursorRecyclerViewAdapter.TrailerViewHolder)view.getTag();
//                viewHolder.imageView_trailer.setImageResource(R.drawable.play_db);
//                viewHolder.imageView_trailer_arrow.setImageResource(R.drawable.arrow);
//
//            }
//            if(type==TYPE_COMMENT){
//                com.patrick.android.hotmovie.adapter.CursorRecyclerViewAdapter.CommentViewHolder viewHolder=(com.patrick.android.hotmovie.adapter.CursorRecyclerViewAdapter.CommentViewHolder)view.getTag();
//                viewHolder.textView_comment.setText(cursor.getString(7));
//                viewHolder.textView_comment_author.setText("");
//
//            }


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



}




