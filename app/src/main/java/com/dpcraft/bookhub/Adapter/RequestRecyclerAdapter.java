package com.dpcraft.bookhub.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dpcraft.bookhub.Activity.BookDetailsActivity;
import com.dpcraft.bookhub.Activity.RequestDetailsActivity;
import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.DataClass.BookPreview;
import com.dpcraft.bookhub.DataClass.RequestPreview;
import com.dpcraft.bookhub.NetModule.Server;
import com.dpcraft.bookhub.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DPC on 2017/2/11.
 */
public class RequestRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_FOOTER = 2;
    private LayoutInflater mInflater;
    private int itemNum;
    private boolean hasMore = true;
    private Context mContext;
    private List<RequestPreview> mRequestPreviewList = new ArrayList<>();

    public RequestRecyclerAdapter(Context context){
        mContext = context;
        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getItemViewType(int position){
        if( position == getItemCount() - 1) {
            return ITEM_TYPE_FOOTER;

        }else{
            return ITEM_TYPE_CONTENT;
        }
    }

    //item显示类型
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(viewType == ITEM_TYPE_FOOTER){
            FooterViewHolder footerViewHolder = new FooterViewHolder(mInflater.inflate(R.layout.item_sell_recycler_footer,parent,false));
            return footerViewHolder;

        } else {
            final ContentViewHolder contentViewHolder = new ContentViewHolder(mInflater.inflate(R.layout.item_request_recyler,parent,false));
            contentViewHolder.requestListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //*获取书籍详细信息*
                    RequestDetailsActivity.actionStart(mContext, contentViewHolder.getRequestId() + "", "data2");


                }
            });
            return contentViewHolder;
        }
    }
    //数据绑定显示
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
        //holder.item_tv.setText(mTitle[position]);
        if(holder instanceof FooterViewHolder){

            if(!hasMore ){
                Log.i("mRequestPreviewL.size",mRequestPreviewList.size() + "");

                if(mRequestPreviewList.size() == 0){
                    ((FooterViewHolder)holder).footerProgressBar.setVisibility(View.GONE);
                    ((FooterViewHolder)holder).footerText.setText(R.string.no_more_request_list);
                }else {
                    ((FooterViewHolder) holder).footerProgressBar.setVisibility(View.GONE);
                    ((FooterViewHolder) holder).footerText.setText(R.string.no_more_request_list);
                }

            }else if(!MyApplication.getInstance().isNetWorkConnected()){
                Log.i("网络未连接", "SellRecyclerAdapter ");
                ((FooterViewHolder)holder).footerProgressBar.setVisibility(View.GONE);
                ((FooterViewHolder)holder).footerText.setText(R.string.network_connection_unavailable);
            }else{
                ((FooterViewHolder)holder).footerProgressBar.setVisibility(View.VISIBLE);
                ((FooterViewHolder)holder).footerText.setText(R.string.request_more_list);
            }
        }
        else{
            RequestPreview requestPreview = mRequestPreviewList.get(position);
            //赋值
            ((ContentViewHolder)holder).userName.setText(requestPreview.getUserInfo().getNickName());
            ((ContentViewHolder)holder).title.setText(requestPreview.getRequestBookInfo().getRequestTitle());
            ((ContentViewHolder)holder).date.setText(requestPreview.getRequestBookInfo().getDate());
            ((ContentViewHolder)holder).setRequestId(requestPreview.getRequestBookInfo().getRequestId());
            String imageUrl = Server.getServerAddress() + "bw/image?bwid=" + requestPreview.getRequestBookInfo().getRequestId() + "&reduce=";
            Glide.with(mContext).load(imageUrl).error(R.drawable.default_user_icon).into(((ContentViewHolder)holder).circleUserIcon);

             }
    }

    //每次加载的item数
    @Override
    public int getItemCount(){
        //return itemNum;
        if(mRequestPreviewList == null){
            return 1;
        }else
        return mRequestPreviewList.size() + 1;
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder{

        View requestListView;
        CircleImageView circleUserIcon;
        String requestId;
        TextView userName, title, date;
        public ContentViewHolder(View view){
            super(view);
            requestListView = view;
            circleUserIcon = (CircleImageView)view.findViewById(R.id.civ_user_icon);
            userName = (TextView)view.findViewById(R.id.tv_user_name);
            title = (TextView)view.findViewById(R.id.tv_request_title);
            date = (TextView)view.findViewById(R.id.tv_date);
        }
        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

    }
    public class FooterViewHolder extends RecyclerView.ViewHolder{
        public TextView footerText;
        public ProgressBar footerProgressBar;
        public FooterViewHolder(View view){
            super(view);
            footerText = (TextView)view.findViewById(R.id.tv_loading_more);
            footerProgressBar = (ProgressBar)view.findViewById(R.id.pb_loading_more);

        }
    }

    public void addRequestPreviewList(List<RequestPreview> requestPreviewList){

        mRequestPreviewList.addAll(requestPreviewList);
        if (requestPreviewList.size() < 5){
            hasMore = false;
        }else {
            hasMore = true;
        }


        Log.i("addBookList==========","done"+ hasMore);
        notifyDataSetChanged();

    }

    public void clearRequestPreviewList(){

        mRequestPreviewList.clear();
        hasMore = true;
    }

    public List<RequestPreview> getRequestPreviewList() {

        return mRequestPreviewList;
    }

    public void setRequestPreviewList(List<RequestPreview> requestPreviewList) {
        this.mRequestPreviewList = requestPreviewList;
        notifyDataSetChanged();
    }


}
