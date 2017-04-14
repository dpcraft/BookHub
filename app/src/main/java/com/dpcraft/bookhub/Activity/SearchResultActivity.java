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

import com.dpcraft.bookhub.Adapter.SearchResultRecyclerAdapter;
import com.dpcraft.bookhub.DataClass.BookGetRequestInformation;
import com.dpcraft.bookhub.DataClass.BookPreview;
import com.dpcraft.bookhub.DataClass.GetBookResponse;
import com.dpcraft.bookhub.NetModule.JSONUtil;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.R;

import java.util.List;


/**
 * Created by DPC on 2017/4/14.
 */
public class SearchResultActivity extends Activity {
    private Toolbar searchToolbar;
    private Button searchButton;
    private TextView searchEditTextView;
    private RecyclerView searchRecyclerView;
    private SearchResultRecyclerAdapter searchResultRecyclerAdapter;
    private BookGetRequestInformation bookGetRequestInformation;
    private List<BookPreview> bookPreviewList;
    private LinearLayoutManager searchLinearLayoutManager;
    private Spinner dealTypeSpinner , orderSpinner;
    private int mIndex = 0;
    private final int length = 5;
    private boolean hasMore = true;
    private String keyword;
    
    private Handler handler= new Handler(){
        public void handleMessage(Message msg){
            Log.i("searchJson=========",msg.obj.toString());
            GetBookResponse getBookResponse = JSONUtil.parseJsonWithGson( msg.obj.toString(),GetBookResponse.class);
            bookPreviewList = getBookResponse.getData();
            mIndex += bookPreviewList.size();
            if(bookPreviewList.size() == 0){
                hasMore = false;
                searchRecyclerView.clearOnScrollListeners();
            }
            if(msg.what == 1) {
                searchResultRecyclerAdapter.clearBookList();
            }
            searchResultRecyclerAdapter.addBookList(bookPreviewList);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initWidget();
        setSpinnerListener();
        searchToolbar.setNavigationIcon(R.drawable.ic_back_simple_black);
        searchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
        searchResultRecyclerAdapter = new SearchResultRecyclerAdapter(this);
        searchRecyclerView.setAdapter(searchResultRecyclerAdapter);
        //设置固定大小
        searchRecyclerView.setHasFixedSize(true);
        //创建线性布局
        searchLinearLayoutManager = new LinearLayoutManager(this);
        searchLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        searchRecyclerView.setLayoutManager(searchLinearLayoutManager);
        Intent intent = getIntent();
        keyword = intent.getStringExtra("KEYWORD");
        searchEditTextView.setText(keyword);
        bookGetRequestInformation = new BookGetRequestInformation();
        bookGetRequestInformation.setKeyWord(keyword);
        requestBookList(mIndex + "" , false);

        searchRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && searchLinearLayoutManager.findLastVisibleItemPosition() + 1 == searchResultRecyclerAdapter.getItemCount()) {

                    requestBookList(mIndex + "" , false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //int lastVisibleItem = sellLinearLayoutManager.findLastVisibleItemPosition();
            }
        });

        searchEditTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.actionStart( SearchResultActivity.this , keyword , "data2");
                finish();
            }
        });
    }

    private void initWidget(){

        searchToolbar = (Toolbar) findViewById(R.id.tb_search);
        searchButton = (Button)findViewById(R.id.btn_search);
        searchEditTextView = (TextView) findViewById(R.id.tv_search_edit);
        searchRecyclerView = (RecyclerView)findViewById(R.id.search_result_recycler);
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
                if (position == 0) {
                    sell = position;
                }else{
                        sell = position - 1;
                }
                bookGetRequestInformation.setSell(sell + "");
                mIndex = 0;
                requestBookList(mIndex + "" , true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id){
                int sell;
                if (position == 0) {
                    sell = position;
                }else{
                    sell = position - 1;
                }
                bookGetRequestInformation.setSell(sell + "");
                mIndex = 0;
                requestBookList(mIndex + "" , true);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra("KEYWORD", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }
}
