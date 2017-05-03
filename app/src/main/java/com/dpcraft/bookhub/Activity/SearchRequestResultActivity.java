package com.dpcraft.bookhub.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dpcraft.bookhub.Adapter.RequestRecyclerAdapter;
import com.dpcraft.bookhub.DataClass.GetRequestPreviewResponse;
import com.dpcraft.bookhub.DataClass.RequestGetRequestInformation;
import com.dpcraft.bookhub.DataClass.RequestPreview;
import com.dpcraft.bookhub.NetModule.JSONUtil;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.UIWidget.Dialog;

import java.util.List;

/**
 * Created by DPC on 2017/5/3.
 */
public class SearchRequestResultActivity extends BaseActivity{

    private RecyclerView searchRecyclerView;
    private RequestRecyclerAdapter requestRecyclerAdapter;
    private LinearLayoutManager requestLinearLayoutManager;
    private Button searchButton;
    private TextView searchTextView;
    private Toolbar searchToolbar;
    private String keyword;
    private RequestGetRequestInformation mRequestGetRequestInformation;
    private List<RequestPreview> mRequestPreviewList;
    private int mIndex = 0,mLastIndex = 0;
    private final int length = 5;
    public final int SUCCESS = 201;
    public final int FAIL = 400;
    private Handler handler= new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == 1 || msg.what == 2) {
                try {
                    GetRequestPreviewResponse getRequestPreviewResponse = JSONUtil.parseJsonWithGson(msg.obj.toString(),GetRequestPreviewResponse.class);
                    switch (getRequestPreviewResponse.getCode()){
                        case SUCCESS:
                        {
                            mRequestPreviewList = getRequestPreviewResponse.getData();
                            mIndex += mRequestPreviewList.size();
                            if (msg.what == 1) {
                                requestRecyclerAdapter.clearRequestPreviewList();
                            }
                            requestRecyclerAdapter.addRequestPreviewList(mRequestPreviewList);
                        }
                        break;
                        case FAIL:
                            Dialog.showDialog("获取数据错误",getRequestPreviewResponse.getMessage(),SearchRequestResultActivity.this);
                            break;
                        default:
                            break;
                    }
                }catch (Exception e){
                    Dialog.showDialog("获取数据错误"," 数据库错误 !",SearchRequestResultActivity.this);
                }

            }

        }
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_request_result);
        initWidget();
        searchToolbar.setNavigationIcon(R.drawable.ic_back_simple_black);
        searchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        requestRecyclerAdapter = new RequestRecyclerAdapter(this);
        searchRecyclerView.setAdapter(requestRecyclerAdapter);
        Intent intent = getIntent();
        keyword = intent.getStringExtra("KEYWORD");
        searchTextView.setText(keyword);
        mRequestGetRequestInformation = new RequestGetRequestInformation();
        mRequestGetRequestInformation.setKeyWord(keyword);
        requestRequestList(mIndex + "" , false);
        //上拉刷新
        searchRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && requestLinearLayoutManager.findLastVisibleItemPosition() + 1 == requestRecyclerAdapter.getItemCount()
                        && mLastIndex < mIndex) {
                    requestRequestList(mIndex + "" , false);
                    mLastIndex = mIndex;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //int lastVisibleItem = sellLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
        

        //设置固定大小
        searchRecyclerView.setHasFixedSize(true);

        //创建线性布局
        requestLinearLayoutManager = new LinearLayoutManager(this);
        requestLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        searchRecyclerView.setLayoutManager(requestLinearLayoutManager);

        searchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.actionStart( SearchRequestResultActivity.this , keyword ,"true");
                finish();
            }
        });
    }


    public void requestRequestList(String index , boolean refresh) {

        mRequestGetRequestInformation.setLength(length + "");
        mRequestGetRequestInformation.setFrom(index);
        Log.i("RgenerateURL()===", mRequestGetRequestInformation.generateURL());
        try {
            NetUtils.getRequestPreviewList(mRequestGetRequestInformation , refresh , handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void initWidget(){
        searchToolbar = (Toolbar) findViewById(R.id.tb_search);
        searchButton = (Button)findViewById(R.id.btn_search);
        searchTextView = (TextView) findViewById(R.id.tv_search_edit);
        searchRecyclerView = (RecyclerView)findViewById(R.id.search_result_recycler);
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, SearchRequestResultActivity.class);
        intent.putExtra("KEYWORD", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }
}
