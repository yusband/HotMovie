package com.patrick.android.hotmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.patrick.android.hotmovie.ui.DetailActivity;

/**
 * Created by Administrator on 2016/11/23.
 */

public class TrueCursorRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    CursorRecyclerViewAdapter cursorRecyclerViewAdapter;
    Context mContext;

    public static final int TYPE_COMMON=0;
    public static final int TYPE_DETAIL=1;
    public static final int TYPE_TRAILER=2;
    public static final int TYPE_COMMENT=3;
    public static final int FRAGMENT_COTENT =10;
    public static final int FRAGMENT_DETAIL=20;
    private int type;
    private int movie_position;

    public TrueCursorRecyclerViewAdapter( CursorRecyclerViewAdapter cursorRecyclerViewAdapter,Context context) {
        this.cursorRecyclerViewAdapter=cursorRecyclerViewAdapter;
        mContext=context;
    }
    public TrueCursorRecyclerViewAdapter( CursorRecyclerViewAdapter cursorRecyclerViewAdapter,Context context,int fragment_type) {
        this(cursorRecyclerViewAdapter,context);
        this.type=fragment_type;
    }
    public TrueCursorRecyclerViewAdapter( CursorRecyclerViewAdapter cursorRecyclerViewAdapter,Context context,int fragment_type,int movie_position) {
        this(cursorRecyclerViewAdapter,context);
        this.type=fragment_type;
        this.movie_position=movie_position;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(type== FRAGMENT_COTENT){

        View view=cursorRecyclerViewAdapter.newView(mContext,cursorRecyclerViewAdapter.getCursor(),parent,CursorRecyclerViewAdapter.TYPE_COMMON);
        return new ViewHolder(view);}
        if(type==FRAGMENT_DETAIL){
          //由getItemType方法返回了DeatilFragment中三种不同的viewholder：detail、trailer、comment；传入到CursorAdapter中的viewType参数，在CursorAdapter中对应了相应的处理逻辑

         View view=cursorRecyclerViewAdapter.newView(mContext,cursorRecyclerViewAdapter.getCursor(),parent,viewType);
                    return new ViewHolder(view);
            }
        return  null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,  final int position) {

        if (type == FRAGMENT_COTENT) {
            // Passing the binding operation to cursor loader
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cursorRecyclerViewAdapter.getCursor().moveToPosition(position);
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("title", cursorRecyclerViewAdapter.getCursor().getString(1));
                    intent.putExtra("position", position);
                    intent.putExtra("table", mContext.getSharedPreferences("sort_order", Context.MODE_PRIVATE).getString("rate", ""));
                    mContext.startActivity(intent);

                    Log.i("contentfragment", "position is " + position);
                    Log.i("contentfragment", "title is " + cursorRecyclerViewAdapter.getCursor().getString(1));
                    Log.i("contentfragment", "table name" + mContext.getSharedPreferences("sort_order", Context.MODE_PRIVATE).getString("rate", ""));

                }
            });
            cursorRecyclerViewAdapter.getCursor().moveToPosition(position);
            //EDITED: added this line as suggested in the comments below, thanks :)
            cursorRecyclerViewAdapter.bindView(holder.itemView, mContext, cursorRecyclerViewAdapter.getCursor(), TYPE_COMMON);
        }
        if (type == FRAGMENT_DETAIL) {

                cursorRecyclerViewAdapter.getCursor().moveToPosition(movie_position);
                Log.i("contentfragment", "movie position is " + movie_position);
                switch (position) {
                    case 0:

                        cursorRecyclerViewAdapter.bindView(holder.itemView, mContext, cursorRecyclerViewAdapter.getCursor(), TYPE_DETAIL);
                        break;
                    case 1:

                        cursorRecyclerViewAdapter.bindView(holder.itemView, mContext, cursorRecyclerViewAdapter.getCursor(), TYPE_TRAILER);
                        break;
                    default:

                        cursorRecyclerViewAdapter.bindView(holder.itemView, mContext, cursorRecyclerViewAdapter.getCursor(), TYPE_COMMENT);
                        break;
                }


        }
    }

    @Override
    public int getItemCount() {
            if(type==FRAGMENT_COTENT){
                Log.i("count", String.valueOf(cursorRecyclerViewAdapter.getCount()));
                return cursorRecyclerViewAdapter.getCount();

            }
        if (type==FRAGMENT_DETAIL){return  3;

        }
        Log.i("count is 0 and type is", String.valueOf(type));
        return 0;
    }
     class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }

        }


    @Override
    public int getItemViewType(int position) {
        if(type==FRAGMENT_DETAIL){
           if(position==0)return TYPE_DETAIL;
           if(position==1)return TYPE_TRAILER;
           if(position>1)return TYPE_COMMENT;
            }

        if(type== FRAGMENT_COTENT){
            return 0;
        }
        return 0;
    }
    }



