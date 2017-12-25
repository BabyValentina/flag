package com.longke.flag.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.systembar.SystemBarHelper;
import com.longke.flag.R;
import com.longke.flag.activity.MyCollectionActivity;
import com.longke.flag.activity.MyViewerActivity2;
import com.longke.flag.activity.MyfansActivity;
import com.longke.flag.activity.SettingActivity;
import com.longke.flag.activity.UserDetailActivity;
import com.longke.flag.adapter.InfoAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment implements OnClickListener {

    @InjectView(R.id.backdrop)
    ImageView mBackdrop;
    @InjectView(R.id.circleimageview_icon_head)
    CircleImageView mCircleimageviewIconHead;
    @InjectView(R.id.user_name)
    TextView mUserName;
    @InjectView(R.id.miaoshu)
    TextView mMiaoshu;
    @InjectView(R.id.viewer_tv)
    TextView mViewerTv;
    @InjectView(R.id.fans_tv)
    TextView mFansTv;
    @InjectView(R.id.colloction_tv)
    TextView mColloctionTv;
    @InjectView(R.id.setting_tv)
    TextView mSettingTv;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @InjectView(R.id.appbar)
    AppBarLayout mAppbar;
    @InjectView(R.id.tabs)
    TabLayout mTabs;
    @InjectView(R.id.viewpager)
    ViewPager mViewpager;
    @InjectView(R.id.coordinatorlayout)
    CoordinatorLayout mCoordinatorlayout;
    private View mView;
    private AppCompatActivity mAppCompatActivity;
    private CircleImageView circleimageview_icon_head;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mAppCompatActivity = (AppCompatActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_me2, container, false);
        circleimageview_icon_head = (CircleImageView) mView.findViewById(R.id.circleimageview_icon_head);
        circleimageview_icon_head.setOnClickListener(this);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) mView.findViewById(R.id.collapsing_toolbar);
        //设置CollapsingToolbarLayout的标题文字
        collapsingToolbar.setTitle(" ");
        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mAppCompatActivity.setSupportActionBar(toolbar);
        mAppCompatActivity.getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // onBackPressed();
            }
        });

        SystemBarHelper.immersiveStatusBar(mAppCompatActivity, 0);
        SystemBarHelper.setHeightAndPadding(mAppCompatActivity, toolbar);
        //设置ViewPager
        ViewPager viewPager = (ViewPager) mView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //设置tablayout，viewpager上的标题
        TabLayout tabLayout = (TabLayout) mView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ea6a6a"));
        setOnclickListener();
        ButterKnife.inject(this, mView);
        return mView;
    }

    private void setOnclickListener() {
        mView.findViewById(R.id.fans_tv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyfansActivity.class));
            }
        });
        mView.findViewById(R.id.setting_tv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });
        mView.findViewById(R.id.viewer_tv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyViewerActivity2.class));
            }
        });
    }

    /**
     * 设置item
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        InfoAdapter adapter = new InfoAdapter(getChildFragmentManager());
        adapter.addFragment(new MessageFragment(), "我的动态");
        adapter.addFragment(new FollowFragment(), "我的flag");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.circleimageview_icon_head:
                //个人资料
                Intent intent = new Intent();
                intent.setClass(getActivity(), UserDetailActivity.class);
                mAppCompatActivity.startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.colloction_tv)
    public void onClick() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), MyCollectionActivity.class);
        mAppCompatActivity.startActivity(intent);
    }
}
