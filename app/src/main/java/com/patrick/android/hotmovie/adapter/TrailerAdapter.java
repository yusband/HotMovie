package com.patrick.android.hotmovie.adapter;

import android.content.Context;
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
    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    LayoutInflater mLayoutInflater;
    Context mContext;
    private static final int NO_TRAILER = 1;
    int position;


    public TrailerAdapter(Context context) {
        super();
        this.mContext = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_detail, parent, false);
        return new TrailerVideoHolder(view);
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
            ((TrailerAdapter.TrailerVideoHolder) holder).imageView.setImageResource(R.drawable.play_big);

        }

    }
}