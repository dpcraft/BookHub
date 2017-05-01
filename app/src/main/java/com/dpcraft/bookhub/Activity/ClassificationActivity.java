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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.dpcraft.bookhub.Adapter.SearchResultRecyclerAdapter;
import com.dpcraft.bookhub.DataClass.BookGetRequestInformation;
import com.dpcraft.bookhub.DataClass.BookPreview;
import com.dpcraft.bookhub.DataClass.GetBookResponse;
import com.dpcraft.bookhub.NetModule.JSONUtil;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.UIWidget.Dialog;

import java.util.List;


/**
 * Created by DPC on 2017/4/14.
 */
public class ClassificationActivity extends BaseActivity {
    private Toolbar classificationToolbar;
    private RecyclerView classificationRecyclerView;
    private SearchResultRecyclerAdapter searchResultRecyclerAdapter;
    private BookGetRequestInformation bookGetRequestInformation;
    private List<BookPreview> bookPreviewList;
    private LinearLayoutManager classificationLinearLayoutManager;
    private Spinner dealTypeSpinner , orderSpinner;
    private int mIndex = 0;
    private final int length = 5;
    private String classification;
    private Handler handler= new Handler(){
        public void handleMessage(Message msg) {
            try {
                if (msg.what == 1 || msg.what == 2) {
                    GetBookResponse getBookResponse = JSONUtil.parseJsonWithGson(msg.obj.toString(), GetBookResponse.class);
                    bookPreviewList = getBookResponse.getData();
                    mIndex += bookPreviewList.size();
                    if (msg.what == 1) {
                        searchResultRecyclerAdapter.clearBookList();
                    }
                    searchResultRecyclerAdapter.addBookList(bookPreviewList);

                }
            }catch (Exception e){
                Dialog.showDialog("获取数据错误"," 数据库错误 !",ClassificationActivity.this);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);
        initWidget();
        setSpinnerListener();
        Intent intent = getIntent();
        classification = intent.getStringExtra("CLASSIFICATION");
        classificationToolbar.setTitle(getBookTypeName(classification));
        classificationToolbar.setNavigationIcon(R.drawable.ic_back_simple_black);
        classificationToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        classificationToolbar.inflateMenu(R.menu.menu_search);
        classificationToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if(menuItemId == R.id.action_search){
                    SearchActivity.actionStart( ClassificationActivity.this , "" , "data2");
                    finish();
                }
                return false;
            }
        });
        
        searchResultRecyclerAdapter = new SearchResultRecyclerAdapter(this);
        classificationRecyclerView.setAdapter(searchResultRecyclerAdapter);
        //设置固定大小
        classificationRecyclerView.setHasFixedSize(true);
        //创建线性布局
        classificationLinearLayoutManager = new LinearLayoutManager(this);
        classificationLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        classificationRecyclerView.setLayoutManager(classificationLinearLayoutManager);
        
        bookGetRequestInformation = new BookGetRequestInformation();
        bookGetRequestInformation.setType(classification);
        requestBookList(mIndex + "" , false);
        classificationRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        &&classificationLinearLayoutManager.findLastVisibleItemPosition() + 1 == searchResultRecyclerAdapter.getItemCount()) {

                    requestBookList(mIndex + "" , false);
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

        classificationToolbar = (Toolbar) findViewById(R.id.tb_classification);
        
        classificationRecyclerView = (RecyclerView)findViewById(R.id.classification_recycler);
        dealTypeSpinner = (Spinner)findViewById(R.id.spin_deal_type);
        orderSpinner = (Spinner)findViewById(R.id.spin_order);

    }
    public void requestBookList(String index , boolean refresh) {
        bookGetRequestInformation.setLength(length + "");
        bookGetRequestInformation.setFrom(index);

        Log.i("generateURL()", bookGetRequestInformation.generateURL());
        try {
            NetUtils.getBookList(bookGetRequestInformation , refresh , handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void  setSpinnerListener(){
        dealTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id){
                int sell;
                if (position != 0) {
                    sell = position - 1;
                    bookGetRequestInformation.setSell(sell + "");
                    mIndex = 0;
                    requestBookList(mIndex + "" , true);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id){
                int order;
                if (position != 0) {
                    order = position - 1;
                bookGetRequestInformation.setOrder(order + "");
                mIndex = 0;
                requestBookList(mIndex + "" , true);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

    }
    private String getBookTypeName(String indexStr){
        int index = Integer.parseInt(indexStr);
        String[] bookTypeNames = getResources().getStringArray(R.array.array_book_type);
        String bookTypeName;
        if(index == 0){
            bookTypeName = bookTypeNames[9];
        }else {
            bookTypeName = bookTypeNames[index];
        }
        return bookTypeName;

    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, ClassificationActivity.class);
        intent.putExtra("CLASSIFICATION", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }
}
