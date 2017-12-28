package com.longke.flag.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longke.flag.R;
import com.longke.flag.activity.DetailsFlagActivity;
import com.longke.flag.adapter.DynamicsAdapter;
import com.longke.flag.adapter.FollowAdapter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDynamicsFragment extends Fragment implements PullLoadMoreRecyclerView.PullLoadMoreListener{
    @InjectView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;


    private int mCount = 1;
    private DynamicsAdapter mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;

    public MyDynamicsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_dynamics, container, false);
        ButterKnife.inject(this, view);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //获取mRecyclerView对象
        mRecyclerView = mPullLoadMoreRecyclerView.getRecyclerView();
        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //设置下拉刷新是否可见
        //mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置是否可以下拉刷新
        //mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
        //设置是否可以上拉刷新
        //mPullLoadMoreRecyclerView.setPushRefreshEnable(false);
        //显示下拉刷新
        mPullLoadMoreRecyclerView.setRefreshing(false);
        mPullLoadMoreRecyclerView.setPullRefreshEnable(false);
        //设置上拉刷新文字
        mPullLoadMoreRecyclerView.setFooterViewText("loading");

        //设置上拉刷新文字颜色
        //mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);
        mPullLoadMoreRecyclerView.setLinearLayout();

        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
        ArrayList<String> list=new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        mRecyclerViewAdapter = new DynamicsAdapter(list);
        mRecyclerViewAdapter.setOnItemClickListener(new FollowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), DetailsFlagActivity.class));
            }
        });
        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
        /*mPullLoadMoreRecyclerView.setSwipeRefreshEnable(false);
        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();*/
        // getData();

    }
    @Override
    public void onRefresh() {
        Log.e("wxl", "onRefresh");
        setRefresh();
        // getData();
    }

    @Override
    public void onLoadMore() {
        Log.e("wxl", "onLoadMore");
        mCount = mCount + 1;
        // getData();
    }

    private void setRefresh() {
        // mRecyclerViewAdapter.clearData();
        mCount = 1;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


}
