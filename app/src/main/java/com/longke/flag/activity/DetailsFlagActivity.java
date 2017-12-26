package com.longke.flag.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.systembar.SystemBarHelper;
import com.longke.flag.R;
import com.longke.flag.common.SimpleFragment;
import com.longke.flag.util.PopuwindowUtils;
import com.longke.flag.view.CommonDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class DetailsFlagActivity extends AppCompatActivity
{


    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @InjectView(R.id.appbar)
    AppBarLayout mAppbar;

    @InjectView(R.id.fab)
    FloatingActionButton mFab;
    @InjectView(R.id.user_name)
    TextView mUserName;
    @InjectView(R.id.progressBar)
    ProgressBar mProgressBar;
    @InjectView(R.id.redirect)
    TextView mRedirect;
    @InjectView(R.id.bottombar_retweet)
    LinearLayout mBottombarRetweet;
    @InjectView(R.id.comment)
    TextView mComment;
    @InjectView(R.id.bottombar_comment)
    LinearLayout mBottombarComment;
    @InjectView(R.id.feedlike)
    TextView mFeedlike;
    @InjectView(R.id.bottombar_attitude)
    LinearLayout mBottombarAttitude;
    @InjectView(R.id.bottombar_layout)
    LinearLayout mBottombarLayout;
    @InjectView(R.id.tabs)
    TabLayout mTabs;
    @InjectView(R.id.viewpager)
    ViewPager mViewpager;
    @InjectView(R.id.topPanel)
    FrameLayout topPanel;
    @InjectView(R.id.rel_content)
    RelativeLayout relContent;
    @InjectView(R.id.success)
    ImageView success;
    @InjectView(R.id.fail)
    ImageView fail;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(DetailsFlagActivity.this).inflate(R.layout.activity_statusbar_immersive_3, null);
        setContentView(view);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        SystemBarHelper.immersiveStatusBar(this, 0);
        SystemBarHelper.setHeightAndPadding(this, mToolbar);

        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener()
        {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset)
            {
//                boolean showTitle = mCollapsingToolbar.getHeight() + verticalOffset <= mToolbar.getHeight();
                boolean showTitle = mCollapsingToolbar.getHeight() + verticalOffset <= mToolbar.getHeight() * 2;
                // mNickname.setVisibility(showTitle ? View.VISIBLE : View.GONE);
            }
        });


        SimpleViewPagerAdapter adapter = new SimpleViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(SimpleFragment.newInstance("关联动态"), "关联动态");
        adapter.addFragment(SimpleFragment.newInstance("评论"), "评论");

        mViewpager.setAdapter(adapter);
        mTabs.setupWithViewPager(mViewpager);

    }

    @OnClick(R.id.bottombar_retweet)
    public void onViewClicked()
    {
        //分享界面弹框
        new PopuwindowUtils(DetailsFlagActivity.this, view, DetailsFlagActivity.this);
        //删除框
        CommonDialog.Show(DetailsFlagActivity.this);
        //成功样式
        success.setVisibility(View.GONE);
        //失败样式
        fail.setVisibility(View.VISIBLE);
        //失败状态确认
        //R.id.lieanr_state;
        //评论转发
        //R.id.rel_zhuanfa
    }


    class SimpleViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public SimpleViewPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title)
        {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position)
        {
            return mFragments.get(position);
        }

        @Override
        public int getCount()
        {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mFragmentTitles.get(position);
        }
    }
}
