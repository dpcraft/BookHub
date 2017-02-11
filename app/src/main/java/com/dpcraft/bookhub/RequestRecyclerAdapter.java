package com.dpcraft.bookhub;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by DPC on 2017/2/11.
 */
public class RequestRecyclerAdapter extends RecyclerView.Adapter<RequestRecyclerAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private int ItemNum =20;
    //private String[]mTitle = null;
    public RequestRecyclerAdapter(Context context){
        this.mInflater = LayoutInflater.from(context);
      //  this.mTitle = new String[20];
    }
    //item显示类型
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = mInflater.inflate(R.layout.item_request_recyler_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    //数据绑定显示
    @Override
    public void onBindViewHolder(ViewHolder holder,int postion){
        //holder.item_tv.setText(mTitle[postion]);
        //此处进行数据赋值等操作
    }
    @Override
    public int getItemCount(){
        return ItemNum ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        // public TextView item_tv;
        public ViewHolder(View view){
            super(view);
            // item_tv =(TextView)view.findViewById(R.id.item_tv);
        }
    }
}
