package com.patrick.android.hotmovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.patrick.android.hotmovie.R;

/**
 * Created by Administrator on 2016/9/1.
 */
public  class TrailerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    CursorRecyclerViewAdapter cursorRecyclerViewAdapter=null;


    public TrailerAdapter(Context context) {
        super();
        this.mContext = context;
    }
    @Override
    public int getItemCount() {

//        return cursorRecyclerViewAdapter.getCursor().getString(1);
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=cursorRecyclerViewAdapter.newView(cursorRecyclerViewAdapter.getCursor(),parent,viewType);
        return new ViewHolder(view);
    }
    private class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    public class CursorRecyclerViewAdapter extends CursorAdapter {private  Context mContext;
        public CursorRecyclerViewAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
            mContext=context;
        }
        public class TrailerVideoHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;
            TextView textView_title;
            CardView cardView;

            public TrailerVideoHolder(View itemView) {
                super(itemView);

                cardView = (CardView) itemView.findViewById(R.id.trailer_detail_card_view);
                textView_title = (TextView) itemView.findViewById(R.id.trailer_detail_card_view_text_name);
                textView = (TextView) itemView.findViewById(R.id.trailer_detail_card_view_text);
                imageView = (ImageView) itemView.findViewById(R.id.trailer_detail_card_view_imageview);

            }

            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                String num = "Trailer   " + (position + 1);
                ((TrailerVideoHolder) holder).textView.setText(num);


                //网络受限，无法访问youtube提供的缩略图资源
//        Picasso.with(mContext).load("http://img.youtube.com/vi/"+id+"/mqdefault.jpg").into(((TrailerAdapter.TrailerVideoHolder) holder).imageView);


            }

        }
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return null;
        }

        public View newView(Cursor cursor, ViewGroup parent, int type) {
            Context context = mContext;
                View view = LayoutInflater.from(context).inflate(R.layout.trailer_detail, parent, false);
               TrailerVideoHolder holder = new TrailerVideoHolder(view);
                view.setTag(holder);
                return view;

        }

        public void bindView(View view, Context context, Cursor cursor,int type,int position) {


//                DetailAdapter.CursorRecyclerViewAdapter.TrailerViewHolder viewHolder=(DetailAdapter.CursorRecyclerViewAdapter.TrailerViewHolder)view.getTag();
//                viewHolder.imageView_trailer.setImageResource(R.drawable.play_db);
//                viewHolder.imageView_trailer_arrow.setImageResource(R.drawable.arrow);

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
}