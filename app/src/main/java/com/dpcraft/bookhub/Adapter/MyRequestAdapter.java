package com.dpcraft.bookhub.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
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
    private List<Integer> bgColorList = new ArrayList<>();
    private Context mContext;
    public MyRequestAdapter(Context context){
        mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_request_recycler, parent, false);
        initBgColor();
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RequestPreview requestPreview = mRequestPreviewList.get(position);
        ((ContentViewHolder)holder).mCardView.setCardBackgroundColor(mContext.getResources().getColor(bgColorList.get(position % 3)));
        ((ContentViewHolder)holder).title.setText(requestPreview.getRequestBookInfo().getRequestTitle());
        ((ContentViewHolder)holder).date.setText(requestPreview.getRequestBookInfo().getDate());
        ((ContentViewHolder)holder).body.loadDataWithBaseURL(null , requestPreview.getRequestBookInfo().getRequestBody(),"text/html","UTF-8",null);
        ((ContentViewHolder)holder).setRequestId(requestPreview.getRequestBookInfo().getRequestId());
    }

    @Override
    public int getItemCount() {
        return mRequestPreviewList.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView title, date;
        WebView body;
        CardView mCardView;
        ImageView delete , next;



        String requestId;

        ContentViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mCardView = (CardView)itemView.findViewById(R.id.cv_request);
            title = (TextView) itemView.findViewById(R.id.tv_request_title);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            body = (WebView)itemView.findViewById(R.id.wv_request_body);
            delete = (ImageView)itemView.findViewById(R.id.iv_delete);
            next = (ImageView)itemView.findViewById(R.id.iv_next);
           // body.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
//            final double d = Math.random();
//            final int i = (int)(d * 3);
//            Log.i("random======", i + "");
//            mCardView.setCardBackgroundColor(mContext.getResources().getColor(bgColorList.get(i)));

        }
        public void setDeleteAlpha(float alpha){
            delete.setAlpha(alpha);
        }
        public void setNextAlpha(float alpha){next.setAlpha(alpha);}
        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

    }


    public void setRequestPreviewList(List<RequestPreview> requestPreviewList){

        mRequestPreviewList = requestPreviewList;

        notifyDataSetChanged();

    }
    public List<RequestPreview> getRequestPreviewList() {
        return mRequestPreviewList;
    }

    private void initBgColor(){
        bgColorList.add(R.color.red_400);
        bgColorList.add(R.color.blue_400);
        bgColorList.add(R.color.green_400);
    }
    public void refreshBgColor(){
        bgColorList.add(bgColorList.get(0));
        bgColorList.remove(0);
        notifyDataSetChanged();
    }
}

