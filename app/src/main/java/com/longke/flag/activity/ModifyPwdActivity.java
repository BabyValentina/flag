package com.longke.flag.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longke.flag.R;
import com.longke.flag.event.MessageEvent;
import com.longke.flag.http.HttpUtil;
import com.longke.flag.http.Urls;
import com.longke.flag.util.Md5Utils;
import com.longke.flag.util.SharedPreferencesUtil;
import com.longke.flag.util.ToastUtil;
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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        if ("ModifyPwd".equals(messageEvent.getTag())) {
            GetSignData(messageEvent.getMessage());
        }
    }
    /**
     * {"FormateStr":"yyyy-MM-dd","ResultCode":200,"Success":true,"Message":"","Data":{"Id":5,"UserName":"18682017798","PhotoUrl":null,"Comments":null,"AttentionCount":0,"BeAttentionCount":0,"CollectCount":0,"FlagList":[],"CircleList":[]},"TotalCount":0,"ContentEncoding":null,"ContentType":null,"JsonRequestBehavior":0,"MaxJsonLength":null,"RecursionLimit":null}
     *
     * @param timestamp
     */
    public void GetSignData(final String timestamp) {
        final String UserCode = (String) SharedPreferencesUtil.get(ModifyPwdActivity.this, SharedPreferencesUtil.UserCode, "");
        String UserSecret = (String) SharedPreferencesUtil.get(ModifyPwdActivity.this, SharedPreferencesUtil.UserSecret, "");
        HttpUtil.getInstance().getOkHttp().get().addHeader("X_MACHINE_ID", "ED5E3E2585B2477ABCA664EAAF32DC2A").
                addHeader("X_REG_SECRET", "er308343cf381bd4a37a185654035475d4c67842").url(Urls.GetSignData)
                .addParam("Timestamp", timestamp)
                .addParam("appKey", UserCode)
                .addParam("appSecret", UserSecret)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            if (response.getBoolean("Success")) {
                                String Message = response.getString("Message");
                                ModifyUser(UserCode, timestamp, Message);
                            } else {
                                ToastUtil.showShort(ModifyPwdActivity.this, response.getString("Message"));
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

    public void ModifyUser(String appKey, String timestamp, String sign) {
        HttpUtil.getInstance().getOkHttp().get().addHeader("X_MACHINE_ID", "ED5E3E2585B2477ABCA664EAAF32DC2A").
                addHeader("X_REG_SECRET", "er308343cf381bd4a37a185654035475d4c67842").url(Urls.ModifyPwd)
                .addParam("timestamp", timestamp)
                .addParam("appKey", appKey)
                .addParam("sign", sign)
                .addParam("mobile",  appKey)
                .addParam("oldPwd", Md5Utils.md5Password(mEtPhone.getText().toString()))
                .addParam("pwd", Md5Utils.md5Password(mConfirmPwd.getText().toString()))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            if (response.getBoolean("Success")) {
                                ToastUtil.showShort(ModifyPwdActivity.this, "修改密码成功");
                                finish();
                            } else {
                                ToastUtil.showShort(ModifyPwdActivity.this, response.getString("Message"));
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
    @OnClick({R.id.iv_back, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_confirm:
                if(TextUtils.isEmpty(mEtPhone.getText().toString())){
                    ToastUtil.showShort(ModifyPwdActivity.this,"旧密码不能为空");
                    return;
                }
                if(TextUtils.isEmpty(mNewPwd.getText())){
                    ToastUtil.showShort(ModifyPwdActivity.this,"新密码不能为空");
                    return;
                }
                if(TextUtils.isEmpty(mConfirmPwd.getText().toString())){
                    ToastUtil.showShort(ModifyPwdActivity.this,"确认密码不能为空");
                    return;
                }
                if(!mNewPwd.getText().toString().equals(mConfirmPwd.getText().toString())){
                    ToastUtil.showShort(ModifyPwdActivity.this,"确认密码和新密码不一致");
                    return;
                }
                HttpUtil.getInstance().GetTimestamp("ModifyPwd");

                break;
        }
    }
}
