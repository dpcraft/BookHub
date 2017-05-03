package com.dpcraft.bookhub.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.dpcraft.bookhub.DataClass.RequestPreview;
import com.dpcraft.bookhub.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DPC on 2017/5/3.
 */
public class MyRequestAdapter extends RecyclerView.Adapter {

    private List<RequestPreview> mRequestPreviewList = new ArrayList<>();
//    public MyRequestAdapter(List<RequestPreview> requestPreviewList){
//        mRequestPreviewList = requestPreviewList;
//    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_request_recycler, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RequestPreview requestPreview = mRequestPreviewList.get(position);
        ((ContentViewHolder)holder).title.setText(requestPreview.getRequestBookInfo().getRequestTitle());
        ((ContentViewHolder)holder).date.setText(requestPreview.getRequestBookInfo().getDate());
        ((ContentViewHolder)holder).body.loadDataWithBaseURL(null , requestPreview.getRequestBookInfo().getRequestBody(),"text/html","UTF-8",null);

    }

    @Override
    public int getItemCount() {
        return mRequestPreviewList.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView title, date;
        WebView body;

        ContentViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            title = (TextView) itemView.findViewById(R.id.tv_request_title);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            body = (WebView)itemView.findViewById(R.id.wv_request_body);

        }

    }


    public void setRequestPreviewList(List<RequestPreview> requestPreviewList){

        mRequestPreviewList = requestPreviewList;

        notifyDataSetChanged();

    }
}

