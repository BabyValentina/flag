package com.longke.flag.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longke.flag.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ModifyPwdActivity extends AppCompatActivity {

    @InjectView(R.id.iv_back)
    ImageView mIvBack;
    @InjectView(R.id.addrss_name_tv)
    TextView mAddrssNameTv;
    @InjectView(R.id.title_bar)
    RelativeLayout mTitleBar;
    @InjectView(R.id.et_phone)
    EditText mEtPhone;
    @InjectView(R.id.new_pwd)
    EditText mNewPwd;
    @InjectView(R.id.confirm_pwd)
    EditText mConfirmPwd;
    @InjectView(R.id.ll_quick_login)
    LinearLayout mLlQuickLogin;
    @InjectView(R.id.btn_confirm)
    Button mBtnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.iv_back, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_confirm:
                finish();
                break;
        }
    }
}
