package com.longke.flag.activity.setting;

import android.content.Intent;
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

public class AddBankCardActivity extends AppCompatActivity {

    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.addrss_name_tv)
    TextView addrssNameTv;
    @InjectView(R.id.title_bar)
    RelativeLayout titleBar;
    @InjectView(R.id.name_et)
    EditText nameEt;
    @InjectView(R.id.type_layout)
    RelativeLayout typeLayout;
    @InjectView(R.id.No_et)
    EditText NoEt;
    @InjectView(R.id.title_r)
    TextView titleR;
    @InjectView(R.id.et_phone_no)
    EditText etPhoneNo;
    @InjectView(R.id.bt_get_sms_code)
    Button btGetSmsCode;
    @InjectView(R.id.confirm_et)
    EditText confirmEt;
    @InjectView(R.id.ll_quick_login)
    LinearLayout llQuickLogin;
    @InjectView(R.id.bt_get_check_code)
    Button btGetCheckCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_card);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.iv_back, R.id.type_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.type_layout:
                startActivity(new Intent(AddBankCardActivity.this,ChooseBankCardActivity.class));
                break;
        }
    }
}
