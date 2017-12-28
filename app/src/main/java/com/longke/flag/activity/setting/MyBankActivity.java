package com.longke.flag.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longke.flag.R;
import com.longke.flag.adapter.BankAdapter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MyBankActivity extends FragmentActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    @InjectView(R.id.back_iv)
    ImageView backIv;
    @InjectView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    @InjectView(R.id.topPanel)
    LinearLayout topPanel;
    @InjectView(R.id.addbank_tv)
    TextView addbankTv;
    private RecyclerView mRecyclerView;
    private BankAdapter mRecyclerViewAdapter;
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bank);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) findViewById(R.id.pullLoadMoreRecyclerView);
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
        mRecyclerViewAdapter = new BankAdapter();
        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);

    }



    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @OnClick({R.id.back_iv, R.id.addbank_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.addbank_tv:
                startActivity(new Intent(MyBankActivity.this,AddBankCardActivity.class));
                break;
        }
    }
}
