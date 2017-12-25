package com.longke.flag.activity.setting;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.longke.flag.R;

public class RechargeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }
}
