package com.patrick.android.hotmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.patrick.android.hotmovie.R;
import com.patrick.android.hotmovie.module.Movie;
import com.patrick.android.hotmovie.ui.CollectActivity;
import com.patrick.android.hotmovie.ui.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 */

public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Movie> list;
    static Context context;
    public CollectAdapter(Context context, List<Movie> list) {
        super();
        this.context=context;
        this.list=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collect_recyclerview, parent, false);
            CollectAdapter.CollectViewHolder holder = new CollectAdapter.CollectViewHolder(view);
            return holder;
        }
        else return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CollectAdapter.CollectViewHolder){
            Log.i("positon bug",list.get(position).getLocal_path());
            Picasso.with(context).load("file://"+list.get(position).getLocal_path()).into(((CollectViewHolder) holder).imageView);
            ((CollectAdapter.CollectViewHolder) holder).textView_title.setText(list.get(position).getTitle());
            ((CollectAdapter.CollectViewHolder) holder).textView_date.setText(list.get(position).getRelease_date());


        }
    }

    @Override
    public int getItemCount() {
        Log.i("s", String.valueOf(CollectActivity.list.size()));
        return CollectActivity.list.size();

    }
    public  class CollectViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView_title;
        TextView textView_date;



        public CollectViewHolder(final View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.collect_recycler_view_image);
            textView_title=(TextView)itemView.findViewById(R.id.collect_recycler_view_text_title);
            textView_date=(TextView)itemView.findViewById(R.id.collect_recycler_view_text_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context,DetailActivity.class);
                    intent.putExtra("identity","CollectActivity");
                    Log.i("collectAdapter", String.valueOf(getAdapterPosition()));
                    intent.putExtra("position",getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }
    public int getItemViewType(int position) {
        if(position>=0){
            return 1;
        }
        else return  2;
    }
}
