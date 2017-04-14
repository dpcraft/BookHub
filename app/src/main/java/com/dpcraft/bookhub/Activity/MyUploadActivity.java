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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.dpcraft.bookhub.Adapter.MyUploadRecyclerAdapter;
import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.DataClass.BookGetRequestInformation;
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
public class MyUploadActivity extends Activity {
   private CustomToolbar customToolbar;
    private RecyclerView myUploadRecyclerView;
    private MyUploadRecyclerAdapter myUploadRecyclerAdapter;
    private List<BookPreview> bookPreviewList;
    private LinearLayoutManager myUploadLinearLayoutManager;
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
                myUploadRecyclerView.clearOnScrollListeners();
            }
            if(msg.what == 1) {
                myUploadRecyclerAdapter.clearBookList();
            }
            myUploadRecyclerAdapter.addBookList(bookPreviewList);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        myApplication = (MyApplication)getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_upload);
        initWidget();
        customToolbar.setTitle("我的发布");
        myUploadRecyclerAdapter = new MyUploadRecyclerAdapter(this);
        myUploadRecyclerView.setAdapter(myUploadRecyclerAdapter);
        //设置固定大小
        myUploadRecyclerView.setHasFixedSize(true);
        //创建线性布局
        myUploadLinearLayoutManager = new LinearLayoutManager(this);
        myUploadLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        myUploadRecyclerView.setLayoutManager(myUploadLinearLayoutManager);

        requestBookList();

        myUploadRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && myUploadLinearLayoutManager.findLastVisibleItemPosition() + 1 == myUploadRecyclerAdapter.getItemCount()) {

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

    private void initWidget(){
        
        myUploadRecyclerView = (RecyclerView)findViewById(R.id.my_upload_recycler);
        customToolbar = (CustomToolbar)findViewById(R.id.ctb_my_upload);
    }
    public void requestBookList(  ) {

        String url = Server.getServerAddress() + "book/user?token=" + myApplication.getToken();
        try {
            NetUtils.getMyBookList(url ,myApplication.getToken() , false ,handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, MyUploadActivity.class);
        intent.putExtra("para1", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }
}
