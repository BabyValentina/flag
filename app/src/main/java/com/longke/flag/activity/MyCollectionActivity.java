package com.longke.flag.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.longke.flag.R;
import com.longke.flag.adapter.MyCollectionAdapter;
import com.longke.flag.util.DialogUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MyCollectionActivity extends AppCompatActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener, MyCollectionAdapter.OnItemClickListener {
    @InjectView(R.id.back_iv)
    ImageView mBackIv;
    @InjectView(R.id.new_create)
    TextView mNewCreate;
    private RecyclerView mRecyclerView;
    private MyCollectionAdapter mRecyclerViewAdapter;
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private Dialog groupDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.inject(this);
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
        mRecyclerViewAdapter = new MyCollectionAdapter();
        mRecyclerViewAdapter.setOnItemClickListener(this);
        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
        View view= LayoutInflater.from(this).inflate(R.layout.new_group_dialog,null,false);
        groupDialog= DialogUtil.dialog(this,view);

    }



    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @OnClick({R.id.back_iv, R.id.new_create})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.new_create:
                groupDialog.show();
                break;
        }
    }
}
