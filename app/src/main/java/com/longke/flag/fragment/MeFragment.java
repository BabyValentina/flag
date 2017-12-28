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
import com.google.gson.Gson;
import com.longke.flag.R;
import com.longke.flag.activity.MyCollectionActivity;
import com.longke.flag.activity.MyViewerActivity2;
import com.longke.flag.activity.MyfansActivity;
import com.longke.flag.activity.SettingActivity;
import com.longke.flag.activity.UserDetailActivity;
import com.longke.flag.adapter.InfoAdapter;
import com.longke.flag.entity.User;
import com.longke.flag.event.MessageEvent;
import com.longke.flag.http.HttpUtil;
import com.longke.flag.http.Urls;
import com.longke.flag.util.SharedPreferencesUtil;
import com.longke.flag.util.ToastUtil;
import com.squareup.picasso.Picasso;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private  User user;

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
        if(mView==null){
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
            EventBus.getDefault().register(this);
            HttpUtil.getInstance().GetTimestamp("UserCenterIndex");
        }


        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        if ("UserCenterIndex".equals(messageEvent.getTag())) {
            GetSignData(messageEvent.getMessage());
        }
    }

    /**
     * {"FormateStr":"yyyy-MM-dd","ResultCode":200,"Success":true,"Message":"","Data":{"Id":5,"UserName":"18682017798","PhotoUrl":null,"Comments":null,"AttentionCount":0,"BeAttentionCount":0,"CollectCount":0,"FlagList":[],"CircleList":[]},"TotalCount":0,"ContentEncoding":null,"ContentType":null,"JsonRequestBehavior":0,"MaxJsonLength":null,"RecursionLimit":null}
     * @param timestamp
     */
    public void GetSignData(final String timestamp){
        final String UserCode= (String) SharedPreferencesUtil.get(mAppCompatActivity, SharedPreferencesUtil.UserCode,"");
        String UserSecret= (String) SharedPreferencesUtil.get(mAppCompatActivity,SharedPreferencesUtil.UserSecret,"");
        HttpUtil.getInstance().getOkHttp().get().addHeader("X_MACHINE_ID", "ED5E3E2585B2477ABCA664EAAF32DC2A").
                addHeader("X_REG_SECRET", "er308343cf381bd4a37a185654035475d4c67842").url(Urls.GetSignData)
                .addParam("Timestamp", timestamp)
                .addParam("appKey",UserCode)
                .addParam("appSecret",UserSecret)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            if(response.getBoolean("Success")){
                               String Message=response.getString("Message");
                                UserCenterIndex(UserCode,timestamp,Message);
                            }else{
                                ToastUtil.showShort(mAppCompatActivity,response.getString("Message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {

                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                    }
                });

    }
    public void UserCenterIndex(String appKey,String timestamp,String sign){
        HttpUtil.getInstance().getOkHttp().get().addHeader("X_MACHINE_ID", "ED5E3E2585B2477ABCA664EAAF32DC2A").
                addHeader("X_REG_SECRET", "er308343cf381bd4a37a185654035475d4c67842").url(Urls.UserCenterIndex)
                .addParam("timestamp", timestamp)
                .addParam("appKey",appKey)
                .addParam("sign",sign)
                .addParam("isOwner",true+"")
                .addParam("isUserData",true+"")
                .addParam("isFlagData",true+"")
                .addParam("isCircleData",true+"")
                .addParam("currPage","1")
                .addParam("pageSize","10")
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            if(response.getBoolean("Success")){
                                String data=response.getString("Data");
                                user=new Gson().fromJson(data, User.class);
                                mUserName.setText(user.getUserName());
                                mMiaoshu.setText(user.getComments());
                                mViewerTv.setText("关注  "+user.getAttentionCount());
                                mFansTv.setText("|  粉丝  "+user.getBeAttentionCount());
                                mColloctionTv.setText("|  收藏  "+user.getCollectCount());
                                Picasso.with(mAppCompatActivity).load(Urls.BASE_URL+user.getPhotoUrl()).into(mCircleimageviewIconHead);
                            }else{
                                ToastUtil.showShort(mAppCompatActivity,response.getString("Message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {

                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                    }
                });

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
        adapter.addFragment(new MyDynamicsFragment(), "我的动态");
        adapter.addFragment(new FlagFragment(), "我的flag");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.circleimageview_icon_head:
                //个人资料
                Intent intent = new Intent();
                intent.setClass(getActivity(), UserDetailActivity.class);
                intent.putExtra("user",user);
                mAppCompatActivity.startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        HttpUtil.getInstance().getOkHttp().cancel(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.colloction_tv)
    public void onClick() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), MyCollectionActivity.class);
        mAppCompatActivity.startActivity(intent);
    }
}
