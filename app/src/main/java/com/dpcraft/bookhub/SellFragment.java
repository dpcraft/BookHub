package com.dpcraft.bookhub;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dpcraft.bookhub.DataClass.BookGetRequestInformation;
import com.dpcraft.bookhub.DataClass.BookPreview;
import com.dpcraft.bookhub.DataClass.GetBookResponse;
import com.dpcraft.bookhub.NetModule.JSONUtil;
import com.dpcraft.bookhub.NetModule.NetUtils;

import java.util.List;

/**
 * Created by DPC on 2017/2/11.
 */
public class SellFragment extends Fragment{
    private RecyclerView sellRecyclerView;
    private RecyclerView.Adapter sellRecyclerAdapter;
    private LinearLayoutManager sellLinearLayoutManager;
    private SwipeRefreshLayout sellSwipeRefreshLayout;
    private BookGetRequestInformation bookGetRequestInformation;
    private  List<BookPreview> bookPreviewList;


    private Handler handler= new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == 201){

                Log.i("json",msg.obj.toString());
                GetBookResponse getBookResponse = JSONUtil.parseJsonWithGson( msg.obj.toString(),GetBookResponse.class);
                bookPreviewList = getBookResponse.getData();
                sellRecyclerAdapter = new SellRecyclerAdapter(getActivity(),bookPreviewList);
                sellRecyclerView.setAdapter(sellRecyclerAdapter);

            }

        }
    };
    public SellFragment(){}
    public static SellFragment newInstance() {
        SellFragment sellFragment = new SellFragment();
        return sellFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell, container, false);
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);




        bookGetRequestInformation = new BookGetRequestInformation();
        sellRecyclerView = (RecyclerView)getActivity().findViewById(R.id.sell_recycler);

        initBookList();
        //上拉刷新
         sellRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && sellLinearLayoutManager.findLastVisibleItemPosition() + 1 == sellRecyclerAdapter.getItemCount()) {
                    sellSwipeRefreshLayout.setRefreshing(true);
                    refreshBookList();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
               //int lastVisibleItem = sellLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
        //设置固定大小
        sellRecyclerView.setHasFixedSize(true);

        //创建线性布局
        sellLinearLayoutManager = new LinearLayoutManager(getActivity());
        sellLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        sellRecyclerView.setLayoutManager(sellLinearLayoutManager);
        //添加分割线
       // sellRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(),sellLinearLayoutManager.VERTICAL,10,
               // ContextCompat.getColor(getActivity(),R.color.blue_500)));
       // sellRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(),sellLinearLayoutManager.VERTICAL,R.drawable.divider_shape));

        //sellRecyclerAdapter = new SellRecyclerAdapter(getActivity());
       // sellRecyclerView.setAdapter(sellRecyclerAdapter);
        sellSwipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.sell_swipe_refresh);
        //设置进度圈的变化颜色
        sellSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.red_900));
        sellSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshBookList();

            }
        });

    }
    public void initBookList() {
        bookGetRequestInformation.setLength("20");
        Log.i("generateURL()", bookGetRequestInformation.generateURL());
        try {
            NetUtils.getBookList(bookGetRequestInformation, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void refreshBookList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //此处处理刷新事件
                        sellSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
