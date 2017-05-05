package com.dpcraft.bookhub.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import android.widget.Toast;


import com.dpcraft.bookhub.Adapter.MyRequestAdapter;
import com.dpcraft.bookhub.Application.MyApplication;

import com.dpcraft.bookhub.DataClass.GetRequestPreviewResponse;
import com.dpcraft.bookhub.DataClass.RequestPreview;
import com.dpcraft.bookhub.DataClass.ResponseFromServer;
import com.dpcraft.bookhub.NetModule.JSONUtil;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.NetModule.Server;
import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.UIWidget.CustomToolbar;
import com.dpcraft.bookhub.UIWidget.Dialog;
import com.dpcraft.bookhub.UIWidget.cardswipelayout.CardConfig;
import com.dpcraft.bookhub.UIWidget.cardswipelayout.CardItemTouchHelperCallback;
import com.dpcraft.bookhub.UIWidget.cardswipelayout.CardLayoutManager;
import com.dpcraft.bookhub.UIWidget.cardswipelayout.OnSwipeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DPC on 2017/5/3.
 */
public class MyRequestActivity extends BaseActivity{
    private CustomToolbar mCustomToolbar;
    private MyApplication mMyApplication;
    private RecyclerView mRecyclerView;
    private  MyRequestAdapter myRequestAdapter;
    private List<RequestPreview> mRequestPreviewList = new ArrayList<>();
    public static int REQUEST_RESPONSE_CODE= 1;
    public static int DELETE_RESPONSE_CODE = 2;



    private Handler handler= new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == REQUEST_RESPONSE_CODE){
                switch (JSONUtil.getResponseCode(msg.obj.toString())){
                    case 201:
                        GetRequestPreviewResponse getRequestPreviewResponse = JSONUtil.parseJsonWithGson(msg.obj.toString(),GetRequestPreviewResponse.class);
                        mRequestPreviewList = getRequestPreviewResponse.getData();
                        myRequestAdapter.setRequestPreviewList(mRequestPreviewList);
                        initView();
                        break;
                    case 400:
                        Dialog.showDialog("", JSONUtil.parseJsonWithGson(msg.obj.toString(),ResponseFromServer.class).getMessage(),MyRequestActivity.this);
                        break;
                    default:
                        break;
                }

            }else if(msg.what == DELETE_RESPONSE_CODE){
                Toast.makeText(MyRequestActivity.this , JSONUtil.parseJsonWithGson(msg.obj.toString(),ResponseFromServer.class).getMessage() , Toast.LENGTH_SHORT).show();

            }
            switch (msg.what) {



            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request);
        initWidget();
        initRequestDetails();

    }

    public void initWidget(){
        mCustomToolbar = (CustomToolbar)findViewById(R.id.ctb_request);
        mCustomToolbar.setTitle("我的求书");
        mRecyclerView = (RecyclerView)findViewById(R.id.my_request_recycler);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myRequestAdapter = new MyRequestAdapter(this);
        mRecyclerView.setAdapter(myRequestAdapter);
    }
    public void initView(){
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(mRecyclerView.getAdapter(),myRequestAdapter.getRequestPreviewList());
        cardCallback.setOnSwipedListener(new OnSwipeListener<RequestPreview>() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                MyRequestAdapter.ContentViewHolder myHolder = (MyRequestAdapter.ContentViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
                if (direction == CardConfig.SWIPING_LEFT) {
                    myHolder.setNextAlpha(Math.abs(ratio));
                    //myHolder.dislikeImageView.setAlpha(Math.abs(ratio));
                    //Toast.makeText(MyRequestActivity.this, "swiping left", Toast.LENGTH_SHORT).show();
                } else if (direction == CardConfig.SWIPING_RIGHT) {
                    myHolder.setDeleteAlpha(Math.abs(ratio));
                   // myHolder.likeImageView.setAlpha(Math.abs(ratio));
                    //Toast.makeText(MyRequestActivity.this, "swiping right", Toast.LENGTH_SHORT).show();
                } else {
                   // myHolder.dislikeImageView.setAlpha(0f);
                    //myHolder.likeImageView.setAlpha(0f);
                    //Toast.makeText(MyRequestActivity.this, "swiping other directions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, RequestPreview o, int direction) {
                MyRequestAdapter.ContentViewHolder myHolder = (MyRequestAdapter.ContentViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1f);
                myHolder.setDeleteAlpha(0f);
                myHolder.setNextAlpha(0f);
                if(direction == CardConfig.SWIPING_LEFT){
                    NetUtils.deleteRequest(mMyApplication.getToken(),myHolder.getRequestId(),handler);
                }

                myRequestAdapter.refreshBgColor();
                //Toast.makeText(MyRequestActivity.this, direction == CardConfig.SWIPED_LEFT ? "swiped left" : "swiped right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipedClear() {
                Toast.makeText(MyRequestActivity.this, "data clear", Toast.LENGTH_SHORT).show();
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshRequestDetails();
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                }, 3000L);
            }

        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager( mRecyclerView, touchHelper);
        mRecyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(mRecyclerView);
    }


    private void initRequestDetails(){
        mMyApplication = (MyApplication)getApplication();
        NetUtils.getRequestDetails(Server.getServerAddress() + "bw/get?token=" + mMyApplication.getToken() + "&bwid=" , handler);
//        String imageUrl = Server.getServerAddress() + "bw/image?bwid=" + requestId + "&reduce=";
//        Glide.with(this).load(imageUrl).error(R.drawable.default_user_icon).into(userIcon);
    }
    private void refreshRequestDetails(){
        if(mRequestPreviewList.size() != 0) {
            myRequestAdapter.setRequestPreviewList(mRequestPreviewList);
        }else {
            initRequestDetails();
        }
    }
    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, MyRequestActivity.class);
        intent.putExtra("para1", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }

}
