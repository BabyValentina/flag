package com.longke.flag.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longke.flag.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * author：wanjianhua on 2017/12/24 12:44
 * email：1243381493@qq.com
 */

public class SexActivity extends AppCompatActivity {
    @InjectView(R.id.iv_add)
    ImageView ivAdd;
    @InjectView(R.id.follow_tv)
    TextView followTv;
    @InjectView(R.id.title_bar)
    RelativeLayout titleBar;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.tv_user)
    TextView tvUser;
    @InjectView(R.id.image_1)
    ImageView image1;
    @InjectView(R.id.rel_man)
    RelativeLayout relMan;
    @InjectView(R.id.tv_sex)
    TextView tvSex;
    @InjectView(R.id.image_3)
    ImageView image3;
    @InjectView(R.id.rel_women)
    RelativeLayout relWomen;
    @InjectView(R.id.iv_save)
    TextView ivSave;
    private String sex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sex_main);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.iv_add, R.id.rel_man, R.id.rel_women})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                finish();
                break;
            case R.id.rel_man:
                sex = "男";
                image1.setVisibility(View.VISIBLE);
                image3.setVisibility(View.GONE);
                break;
            case R.id.rel_women:
                sex = "女";
                image1.setVisibility(View.GONE);
                image3.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick(R.id.iv_save)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.putExtra("sex", sex);
        setResult(RESULT_OK, intent);
        finish();
    }
}
