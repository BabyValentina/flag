package com.longke.flag.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longke.flag.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * author：wanjianhua on 2017/12/24 11:54
 * email：1243381493@qq.com
 */

public class UserNameActivity extends AppCompatActivity {

    @InjectView(R.id.iv_add)
    ImageView ivAdd;
    @InjectView(R.id.follow_tv)
    TextView followTv;
    @InjectView(R.id.iv_save)
    TextView ivSave;
    @InjectView(R.id.title_bar)
    RelativeLayout titleBar;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.et_sign)
    EditText etSign;

    private String sign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username_main);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        etSign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sign = charSequence.toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick({R.id.iv_add, R.id.iv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                finish();
                break;
            case R.id.iv_save:
                Intent intent = new Intent();
                intent.putExtra("username", sign);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }

    }
}
