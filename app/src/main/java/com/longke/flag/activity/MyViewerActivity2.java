package com.longke.flag.activity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.longke.flag.R;
import com.longke.flag.adapter.FansAdapter;
import com.longke.flag.adapter.FlagAdapter;
import com.longke.flag.adapter.FollowAdapter;
import com.longke.flag.adapter.ViewerAdapter;
import com.longke.flag.util.DialogUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

public class MyViewerActivity2 extends FragmentActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener, FollowAdapter.OnItemClickListener {

    private Dialog fansDialog;
    private RecyclerView mRecyclerView,mRecyclerView2;
    private FansAdapter mRecyclerViewAdapter;
    private FlagAdapter mRecyclerViewAdapter2;
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView,mPullLoadMoreRecyclerView2;
    private TextView viewedTV,flagTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_viewer2);
        initView();
    }

    private void initView(){
        mPullLoadMoreRecyclerView= (PullLoadMoreRecyclerView) findViewById(R.id.pullLoadMoreRecyclerView);

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
        //设置上拉刷新文字
        mPullLoadMoreRecyclerView.setFooterViewText("loading");
        //设置上拉刷新文字颜色
        //mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);
        mPullLoadMoreRecyclerView.setLinearLayout();

        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
        mRecyclerViewAdapter = new FansAdapter();
        mRecyclerViewAdapter.setOnItemClickListener(this);
        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);

        mPullLoadMoreRecyclerView2=(PullLoadMoreRecyclerView) findViewById(R.id.pullLoadMoreRecyclerView2);
        mRecyclerView2 = mPullLoadMoreRecyclerView2.getRecyclerView();
        //代码设置scrollbar无效？未解决！
        mRecyclerView2.setVerticalScrollBarEnabled(true);
        //设置下拉刷新是否可见
        //mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置是否可以下拉刷新
        //mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
        //设置是否可以上拉刷新
        //mPullLoadMoreRecyclerView.setPushRefreshEnable(false);
        //显示下拉刷新
        mPullLoadMoreRecyclerView2.setRefreshing(false);
        //设置上拉刷新文字
        mPullLoadMoreRecyclerView2.setFooterViewText("loading");
        //设置上拉刷新文字颜色
        //mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);
        mPullLoadMoreRecyclerView2.setLinearLayout();

        mPullLoadMoreRecyclerView2.setOnPullLoadMoreListener(this);
        mRecyclerViewAdapter2 = new FlagAdapter();
        mPullLoadMoreRecyclerView2.setAdapter(mRecyclerViewAdapter2);

        View view= LayoutInflater.from(this).inflate(R.layout.dialog_viewer,null,false);
        fansDialog= DialogUtil.dialog(this,view);

        viewedTV= (TextView) findViewById(R.id.viewed_tv);
        flagTV= (TextView) findViewById(R.id.flag_tv);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.viewed_layout:
                flagTV.setTextColor(Color.parseColor("#8B8B8B"));
                viewedTV.setTextColor(Color.parseColor("#000000"));
                findViewById(R.id.line1).setVisibility(View.VISIBLE);
                findViewById(R.id.line2).setVisibility(View.INVISIBLE);
                mPullLoadMoreRecyclerView.setVisibility(View.VISIBLE);
                mPullLoadMoreRecyclerView2.setVisibility(View.GONE);
                break;
            case R.id.flag_layout:
                findViewById(R.id.line1).setVisibility(View.INVISIBLE);
                findViewById(R.id.line2).setVisibility(View.VISIBLE);
                viewedTV.setTextColor(Color.parseColor("#8B8B8B"));
                flagTV.setTextColor(Color.parseColor("#000000"));
                mPullLoadMoreRecyclerView.setVisibility(View.GONE);
                mPullLoadMoreRecyclerView2.setVisibility(View.VISIBLE);
                break;
            case R.id.cancel_tv:
                fansDialog.dismiss();
                break;
            case R.id.confirm_tv:
                fansDialog.dismiss();
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onItemClick(View view, int position) {
        fansDialog.show();
    }
}
