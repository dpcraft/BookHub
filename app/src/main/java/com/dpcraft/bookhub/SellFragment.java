package com.dpcraft.bookhub;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by DPC on 2017/2/11.
 */
public class SellFragment extends Fragment{
    private RecyclerView sellRecyclerView;
    private RecyclerView.Adapter sellRecyclerAdapter;
    private LinearLayoutManager sellLinearLayoutManager;
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
        sellRecyclerView = (RecyclerView)getActivity().findViewById(R.id.sell_recycler);
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
        sellRecyclerAdapter = new SellRecyclerAdapter(getActivity());
        sellRecyclerView.setAdapter(sellRecyclerAdapter);


    }
}
