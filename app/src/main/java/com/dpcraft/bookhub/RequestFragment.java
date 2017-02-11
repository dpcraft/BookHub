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
public class RequestFragment extends Fragment{
    private RecyclerView requestRecyclerView;
    private RecyclerView.Adapter requestRecyclerAdapter;
    private LinearLayoutManager requestLinearLayoutManager;
    public RequestFragment(){}
    public static RequestFragment newInstance() {
        RequestFragment RequestFragment = new RequestFragment();
        return RequestFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        requestRecyclerView = (RecyclerView)getActivity().findViewById(R.id.request_recycler);
        //设置固定大小
        requestRecyclerView.setHasFixedSize(true);

        //创建线性布局
        requestLinearLayoutManager = new LinearLayoutManager(getActivity());
        requestLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        requestRecyclerView.setLayoutManager(requestLinearLayoutManager);
        //添加分割线
        // requestRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(),requestLinearLayoutManager.VERTICAL,10,
        // ContextCompat.getColor(getActivity(),R.color.blue_500)));
        // requestRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(),requestLinearLayoutManager.VERTICAL,R.drawable.divider_shape));
        requestRecyclerAdapter = new RequestRecyclerAdapter(getActivity());
        requestRecyclerView.setAdapter(requestRecyclerAdapter);


    }
}
