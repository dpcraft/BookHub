package com.dpcraft.bookhub.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dpcraft.bookhub.Adapter.MyIntentionRecyclerAdapter;
import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.DataClass.BookPreview;
import com.dpcraft.bookhub.DataClass.GetBookResponse;
import com.dpcraft.bookhub.NetModule.JSONUtil;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.NetModule.Server;
import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.UIWidget.CustomToolbar;

import java.util.List;


/**
 * Created by DPC on 2017/4/14.
 */
public class MyIntentionActivity extends BaseActivity {
   private CustomToolbar customToolbar;
    private RecyclerView myIntentionRecyclerView;
    private MyIntentionRecyclerAdapter myIntentionRecyclerAdapter;
    private List<BookPreview> bookPreviewList;
    private LinearLayoutManager myIntentionLinearLayoutManager;
    private MyApplication myApplication;
    private int mIndex = 0;

    private boolean hasMore = true;

    
    private Handler handler= new Handler(){
        public void handleMessage(Message msg){
            Log.i("searchJson=========",msg.obj.toString());
            GetBookResponse getBookResponse = JSONUtil.parseJsonWithGson( msg.obj.toString(),GetBookResponse.class);
            bookPreviewList = getBookResponse.getData();
            mIndex += bookPreviewList.size();
            if(bookPreviewList.size() == 0){
                hasMore = false;
                myIntentionRecyclerView.clearOnScrollListeners();
            }
            if(msg.what == 1) {
                myIntentionRecyclerAdapter.clearBookList();
            }
            myIntentionRecyclerAdapter.addBookList(bookPreviewList);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        myApplication = (MyApplication)getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_upload);
        initWidget();
        customToolbar.setTitle("我的有意");
        myIntentionRecyclerAdapter = new MyIntentionRecyclerAdapter(this);
        myIntentionRecyclerView.setAdapter(myIntentionRecyclerAdapter);
        //设置固定大小
        myIntentionRecyclerView.setHasFixedSize(true);
        //创建线性布局
        myIntentionLinearLayoutManager = new LinearLayoutManager(this);
        myIntentionLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        myIntentionRecyclerView.setLayoutManager(myIntentionLinearLayoutManager);

        //requestBookList();

        myIntentionRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && myIntentionLinearLayoutManager.findLastVisibleItemPosition() + 1 == myIntentionRecyclerAdapter.getItemCount()) {

                   // requestBookList();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //int lastVisibleItem = sellLinearLayoutManager.findLastVisibleItemPosition();
            }
        });

    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i("onResume========","onResume");
        myIntentionRecyclerAdapter.clearBookList();
        requestBookList();
    }

    private void initWidget(){
        
        myIntentionRecyclerView = (RecyclerView)findViewById(R.id.my_upload_recycler);
        customToolbar = (CustomToolbar)findViewById(R.id.ctb_my_upload);
    }
    public void requestBookList(  ) {

        String url = Server.getServerAddress() + "book/user?token=" + myApplication.getToken();
        try {
            NetUtils.getMyBookList(url ,myApplication.getToken() , true ,handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, MyIntentionActivity.class);
        intent.putExtra("para1", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }
}
