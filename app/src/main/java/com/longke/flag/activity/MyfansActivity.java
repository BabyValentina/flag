package com.longke.flag.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.longke.flag.R;
import com.longke.flag.adapter.FansAdapter;
import com.longke.flag.adapter.FollowAdapter;
import com.longke.flag.util.DialogUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import butterknife.OnClick;

public class MyfansActivity extends FragmentActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener, FollowAdapter.OnItemClickListener {

    private Dialog fansDialog;
    private RecyclerView mRecyclerView;
    private FansAdapter mRecyclerViewAdapter;
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfans);
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

        View view= LayoutInflater.from(this).inflate(R.layout.dialog_viewer,null,false);
        fansDialog= DialogUtil.dialog(this,view);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_iv:
                finish();
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
