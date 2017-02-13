package com.dpcraft.bookhub;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by DPC on 2017/2/11.
 */
public class SellRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;

    private LayoutInflater mInflater;
    private int ItemNum = 20;
    public SellRecyclerAdapter(Context context){
        this.mInflater = LayoutInflater.from(context);
    }
    //判断当前Item是否为HeadView
    public boolean isHeadView(int position){
        return position == 0;
    }
    @Override
    public int getItemViewType(int position){
        if(isHeadView(position)){
            return ITEM_TYPE_HEADER;
        }else{
            return ITEM_TYPE_CONTENT;
        }
    }

    //item显示类型
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(viewType == ITEM_TYPE_HEADER){
            return new HeaderViewHolder(mInflater.inflate(R.layout.item_recycler_header,parent,false));
        }else {
            return new ContentViewHolder(mInflater.inflate(R.layout.item_sell_recycler_layout,parent,false));
        }
    }
    //数据绑定显示
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
        //holder.item_tv.setText(mTitle[position]);
        if (holder instanceof HeaderViewHolder) {
        }
        else{
            //((ContentViewHolder) holder).textView.setText(texts[position - mHeaderCount]);
             }
    }

    //每次加载的item数
    @Override
    public int getItemCount(){
        return ItemNum ;
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder{
       // public TextView item_tv;
        public ContentViewHolder(View view){
            super(view);
           // item_tv =(TextView)view.findViewById(R.id.item_tv);
        }
    }
    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        // public TextView item_tv;
        public HeaderViewHolder(View view){
            super(view);
            // item_tv =(TextView)view.findViewById(R.id.item_tv);
        }
    }

}
