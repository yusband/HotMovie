package com.patrick.android.hotmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.patrick.android.hotmovie.R;
import com.patrick.android.hotmovie.TrailerActivity;

/**
 * Created by Administrator on 2016/9/1.
 */
public  class TrailerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    LayoutInflater mLayoutInflater;
    Context mContext;
    int position;


    public TrailerAdapter(Context context) {
        super();
        this.mContext = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_detail,parent,false);
        return  new TrailerVideoHolder(view);
    }

    public  class TrailerVideoHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CardView cardView;

        public  TrailerVideoHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.trailer_detail_card_view);
            textView = (TextView) itemView.findViewById(R.id.trailer_detail_card_view_text);
            imageView = (ImageView)itemView. findViewById(R.id.trailer_detail_card_view_imageview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?"+TrailerActivity.trailerList.get(getLayoutPosition()))));
                }
            });

        }
    }
public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    String num="Trailer   "+  (position+1);
        ( (TrailerVideoHolder) holder).textView.setText(num);
        String id=TrailerActivity.trailerList.get(position);

         //网络受限，无法访问youtube提供的缩略图资源
//        Picasso.with(mContext).load("http://img.youtube.com/vi/"+id+"/mqdefault.jpg").into(((TrailerAdapter.TrailerVideoHolder) holder).imageView);
    ((TrailerAdapter.TrailerVideoHolder) holder).imageView.setImageResource(R.drawable.play_big);
    }

    @Override
    public int getItemCount() {
        return TrailerActivity.trailerList.size();
    }
}